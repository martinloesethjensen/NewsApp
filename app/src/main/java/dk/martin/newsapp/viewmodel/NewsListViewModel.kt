package dk.martin.newsapp.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import dk.martin.newsapp.model.Article
import dk.martin.newsapp.service.module.NetworkModule
import dk.martin.newsapp.view.adapter.ArticleRecyclerAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NewsListViewModel : ViewModel() {
    private lateinit var subscription: Disposable
    private var articles = MutableLiveData<List<Article>>()
    private val articleRecyclerAdapter = ArticleRecyclerAdapter()

    init {
        initializeArticlesFromApi()
    }

    fun getArticles() = articles.value

    fun getAdapter() = articleRecyclerAdapter

    private fun initializeArticlesFromApi() {
        Log.d("initializeArticlesFromApi", "Running initializeArticlesFromApi()")

        val retrofit = NetworkModule.provideRetrofitInterface()

        val apiService = NetworkModule.provideNewsApi(retrofit)

        subscription = apiService.getArticles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({ result ->
                Log.d("initializeArticlesFromApi", result.toString())
                this.articles.value = result.articles as ArrayList<Article>

                onSuccess(result.articles as ArrayList<Article>)

                Log.d("initializeArticlesFromApi", "Articles size: ${(this.articles.value as ArrayList<Article>).size}")
            }, {
                Log.d("initializeArticlesFromApi", it.message)
            })
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun onSuccess(articles: List<Article>) {
        articles.let { articleRecyclerAdapter.updateArticleList(it) }
    }
}