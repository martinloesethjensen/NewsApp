package dk.martin.newsapp.viewmodel

import android.util.Log
import android.view.View
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
    private val mutableLoadingVisibility: MutableLiveData<Int> = MutableLiveData()
    private val loadingVisibility: LiveData<Int>
    private val mutableArticles = MutableLiveData<List<Article>>()
    private val articles: LiveData<List<Article>>
    private val groupAdapter = GroupAdapter<ViewHolder>()

    init {
        loadingVisibility = mutableLoadingVisibility
        articles = mutableArticles
        initializeArticlesFromApi()
    }

    fun getArticles() = articles

    fun getLoadingVisibility() = loadingVisibility

    fun getGroupAdapter() = groupAdapter

    private fun initializeArticlesFromApi() {
        Log.d("initializeArticlesFromApi", "Running initializeArticlesFromApi()")

        val retrofit = NetworkModule.provideRetrofitInterface()

        val apiService = NetworkModule.provideNewsApi(retrofit)

        subscription = apiService.getArticles("Adapt agencies")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveArticleListStart() }
            .doOnTerminate { onRetrieveArticleListFinish() }
            .subscribe({ result ->
                mutableArticles.value = result.articles as List<Article>

                Log.d(
                    "initializeArticlesFromApi",
                    "Articles size: ${(mutableArticles.value as List<Article>).size}"
                )
            }, {
                Log.d("initializeArticlesFromApi", it.message)
            })
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun onRetrieveArticleListStart() {
        mutableLoadingVisibility.value = View.VISIBLE

    }

    private fun onRetrieveArticleListFinish() {
        mutableLoadingVisibility.value = View.GONE
    }
}