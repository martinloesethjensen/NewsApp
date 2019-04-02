package dk.martin.newsapp.ui.article

import android.arch.lifecycle.MutableLiveData
import android.view.View
import dk.martin.newsapp.R
import dk.martin.newsapp.base.BaseViewModel
import dk.martin.newsapp.model.ArticleList
import dk.martin.newsapp.network.NewsApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ArticleListViewModel : BaseViewModel() {

    @Inject
    lateinit var articleApi: NewsApiService

    private lateinit var subscription: Disposable
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadArticles() }
    val articleListAdapter: ArticleListAdapter = ArticleListAdapter()

    init {
        loadArticles()
    }

    private fun loadArticles() {
        subscription = articleApi.getArticles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveArticleListStart() }
            .doOnTerminate { onRetrieveArticleListFinish() }
            .subscribe(
                { result -> onRetrieveArticleListSuccess(result) },
                { onRetrieveArticleListError() }
            )
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun onRetrieveArticleListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveArticleListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveArticleListSuccess(articleList: ArticleList) {
        articleListAdapter.updateArticleList(articleList)
    }

    private fun onRetrieveArticleListError() {
        errorMessage.value = R.string.post_error
    }
}