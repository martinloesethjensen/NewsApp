package dk.martin.newsapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import dk.martin.newsapp.api.NewsApiService
import dk.martin.newsapp.model.Article
import dk.martin.newsapp.model.ArticleList
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.article_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors


class NewsActivity : AppCompatActivity() {

    //private var articlePosition = POSITION_NOT_SET
    var articles = ArrayList<Article>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_list)
        setSupportActionBar(toolbar)

        initializeArticlesFromApi()

        listArticles.layoutManager = LinearLayoutManager(this)

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

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .callbackExecutor(Executors.newSingleThreadExecutor())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val apiService = retrofit.create(NewsApiService::class.java)

        val articlesFromApi = apiService.getArticles()

        articlesFromApi.enqueue(object : Callback<ArticleList> {
            override fun onFailure(call: Call<ArticleList>, throwable: Throwable) {
                if (call.isCanceled) Log.d("initializeArticlesFromApi", throwable.message)
            }

            override fun onResponse(call: Call<ArticleList>, response: Response<ArticleList>) {
                if (response.isSuccessful) {
                    Log.d("initializeArticlesFromApi", response.body().toString())
                    val articleList: ArticleList? = response.body()
                    articles = articleList?.articles as ArrayList<Article>

                    this@NewsActivity.runOnUiThread {
                        onPostExecute()
                    }

                    Log.d("initializeArticlesFromApi", "Articles size: ${articles.size}")
                } else if (articles.size != 0 && call.isExecuted) {
                    call.cancel()
                }
            }
        })
    }

    fun onPostExecute() {
        val adapter = ArticleRecyclerAdapter(applicationContext, articles)
        listArticles.adapter = adapter
    }
}
