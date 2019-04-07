package dk.martin.newsapp.view.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import dk.martin.newsapp.R
import dk.martin.newsapp.model.Article
import dk.martin.newsapp.service.module.NetworkModule
import dk.martin.newsapp.view.adapter.ArticleRecyclerAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.article_list.*


class NewsListActivity : AppCompatActivity() {

    var articles = ArrayList<Article>()
    private lateinit var subscribe: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_list)
        setSupportActionBar(toolbar)

        initializeArticlesFromApi()

        listArticles.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        listArticles.adapter = ArticleRecyclerAdapter(this, articles)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initializeArticlesFromApi() {
        Log.d("initializeArticlesFromApi", "Running initializeArticlesFromApi()")

        val retrofit = NetworkModule.provideRetrofitInterface()

        val apiService = NetworkModule.provideNewsApi(retrofit)

        subscribe = apiService.getArticles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.d("initializeArticlesFromApi", result.toString())
                    articles = result?.articles as ArrayList<Article>
                    onPostExecute() // important for refreshing the list, so it's not blank
                    Log.d("initializeArticlesFromApi", "Articles size: ${articles.size}")
                },
                { error ->
                    Log.d("initializeArticlesFromApi", "Error with fetching articles from api: ${error.message}")
                }
            )
    }

    private fun onPostExecute() {
        val adapter = ArticleRecyclerAdapter(applicationContext, articles)
        listArticles.adapter = adapter
        subscribe.dispose()
    }
}
