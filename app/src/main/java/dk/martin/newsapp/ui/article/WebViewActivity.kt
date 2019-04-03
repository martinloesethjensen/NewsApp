package dk.martin.newsapp.ui.article

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import dk.martin.newsapp.R
import dk.martin.newsapp.utils.ARTICLE_URL

class WebViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_webview)

        val extra: Bundle? = intent.extras

        Log.d("WebView", "ARTICLE_URL: ${extra?.getString(ARTICLE_URL)}")

        val webView: WebView = findViewById(R.id.web_view)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return false
            }
        }
        webView.loadUrl(extra?.getString(ARTICLE_URL))
    }
}