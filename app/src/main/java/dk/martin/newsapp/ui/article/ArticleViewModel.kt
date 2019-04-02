package dk.martin.newsapp.ui.article

import android.arch.lifecycle.MutableLiveData
import dk.martin.newsapp.base.BaseViewModel
import dk.martin.newsapp.model.Article

class ArticleViewModel : BaseViewModel() {
    private val articleTitle = MutableLiveData<String>()
    private val articleDescription = MutableLiveData<String>()
    private val articleAuthor = MutableLiveData<String>()
    private val articleSourceId = MutableLiveData<String>()
    private val articleSourceName = MutableLiveData<String>()
    private val articleContent = MutableLiveData<String>()
    private val articlePublishedAt = MutableLiveData<String>()
    private val articleUrl = MutableLiveData<String>()
    private val articleUrlToImage = MutableLiveData<String>()

    fun bind(article: Article) {
        articleTitle.value = article.title
        articleDescription.value = article.description
        articleAuthor.value = article.author
        articleSourceId.value = article.source?.id
        articleSourceName.value = article.source?.name
        articleContent.value = article.content
        articlePublishedAt.value = article.publishedAt
        articleUrl.value = article.url
        articleUrlToImage.value = article.urlToImage
    }

    fun getArticleTitle(): MutableLiveData<String> {
        return articleTitle
    }

    fun getArticleDescription(): MutableLiveData<String> {
        return articleDescription
    }

    fun getArticleAuthor(): MutableLiveData<String> {
        return articleAuthor
    }

    fun getArticleSourceId(): MutableLiveData<String> {
        return articleSourceId
    }

    fun getArticleSourceName(): MutableLiveData<String> {
        return articleSourceName
    }

    fun getArticleContent(): MutableLiveData<String> {
        return articleContent
    }

    fun getArticlePublishedAt(): MutableLiveData<String> {
        return articlePublishedAt
    }

    fun getArticleUrl(): MutableLiveData<String> {
        return articleUrl
    }

    fun getArticleUrlToImage(): MutableLiveData<String> {
        return articleUrlToImage
    }
}