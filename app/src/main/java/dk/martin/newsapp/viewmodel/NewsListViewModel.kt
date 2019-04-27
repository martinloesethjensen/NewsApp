package dk.martin.newsapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import dk.martin.newsapp.model.Article
import dk.martin.newsapp.service.module.NetworkModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NewsListViewModel : ViewModel() {
    private lateinit var subscription: Disposable
    private val mutableArticles = MutableLiveData<List<Article>>()
    private val groupAdapter = GroupAdapter<ViewHolder>()
    var articles: LiveData<List<Article>> = mutableArticles

    init {
        initializeArticlesFromApi()
    }

    fun getArticles() = articles.value

    fun getGroupAdapter() = groupAdapter

    private fun initializeArticlesFromApi() {
        Log.d("initializeArticlesFromApi", "Running initializeArticlesFromApi()")

        val retrofit = NetworkModule.provideRetrofitInterface()

        val apiService = NetworkModule.provideNewsApi(retrofit)

        subscription = apiService.getArticles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                Log.d("initializeArticlesFromApi", result.toString())

                this.mutableArticles.value = result.articles as List<Article>

                Log.d(
                    "initializeArticlesFromApi",
                    "Articles size: ${(this.mutableArticles.value as List<Article>).size}"
                )
            }, {
                Log.d("initializeArticlesFromApi", it.message)
            })
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}