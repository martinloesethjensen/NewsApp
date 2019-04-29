package dk.martin.newsapp.view.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dk.martin.newsapp.R
import dk.martin.newsapp.view.groupie.item.ArticleItem
import dk.martin.newsapp.viewmodel.NewsListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.article_list.*


class NewsListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_list)
        setSupportActionBar(toolbar)

        initializeUi()
    }

    private fun initializeUi() {
        val viewModel = ViewModelProviders.of(this).get(NewsListViewModel::class.java)
        val groupAdapter = viewModel.getGroupAdapter()

        viewModel.getLoadingVisibility().observe(this, Observer {
            progress_circular_article_list.visibility = it
        })

        viewModel.getArticles().observe(this, Observer { articles ->
            articles ?: return@Observer
            groupAdapter.update(articles.map(::ArticleItem))
        })

        listArticles.apply {
            layoutManager = LinearLayoutManager(this@NewsListActivity)
            adapter = groupAdapter
        }
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
}
