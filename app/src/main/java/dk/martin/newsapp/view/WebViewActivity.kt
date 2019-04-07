package dk.martin.newsapp.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import dk.martin.newsapp.R
import dk.martin.newsapp.ui.article.WebViewViewModel

class WebViewActivity : AppCompatActivity() {

    private lateinit var viewModel: WebViewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.article_webview)

        viewModel = ViewModelProviders.of(this).get(WebViewViewModel::class.java)

        val webView: WebView = findViewById(R.id.web_view)
        val extra: Bundle? = intent.extras

        viewModel.loadWebView(extra, webView)
    }
}