package dk.martin.newsapp.ui.article

import android.arch.lifecycle.ViewModel
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import dk.martin.newsapp.utils.ARTICLE_URL

class WebViewViewModel : ViewModel() {
    fun loadWebView(extras: Bundle?, webView: WebView) {
        Log.d("WebView", "ARTICLE_URL: ${extras?.getString(ARTICLE_URL)}")
        webView.webViewClient =
            object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url)
                    return false
                }
            }
        webView.loadUrl(extras?.getString(ARTICLE_URL))
    }
}