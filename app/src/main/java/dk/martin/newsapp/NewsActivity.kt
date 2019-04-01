package dk.martin.newsapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import dk.martin.newsapp.api.ApiDataManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.article_list.*
import android.R.attr.data
import android.text.method.TextKeyListener.clear



class NewsActivity : AppCompatActivity() {

    //private var articlePosition = POSITION_NOT_SET

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_list)
        setSupportActionBar(toolbar)

        listArticles.layoutManager = LinearLayoutManager(this)

        listArticles.adapter = ArticleRecyclerAdapter(this, ApiDataManager.articles)
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
    //TODO: notifyDataSetChanged
}
