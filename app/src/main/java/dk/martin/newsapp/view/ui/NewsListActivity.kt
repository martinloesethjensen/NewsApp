package dk.martin.newsapp.view.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import dk.martin.newsapp.R
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
        viewModel.getArticles()

        listArticles.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        listArticles.adapter = viewModel.getAdapter()
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
