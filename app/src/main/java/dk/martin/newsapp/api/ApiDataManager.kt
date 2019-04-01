package dk.martin.newsapp.api

import android.util.Log
import dk.martin.newsapp.BASE_URL
import dk.martin.newsapp.NewsActivity
import dk.martin.newsapp.model.Article
import dk.martin.newsapp.model.ArticleList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors


object ApiDataManager {

    var articles = ArrayList<Article>()

    init {
        initializeArticlesFromApi()

    }


    private fun initializeArticlesFromApi() {
        Log.d("initializeArticlesFromApi", "Running initializeArticlesFromApi()")

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .callbackExecutor(Executors.newSingleThreadExecutor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(NewsApiService::class.java)

        val articlesFromApi = apiService.getArticles()

        articlesFromApi.enqueue(object : Callback<ArticleList> {
            override fun onFailure(call: Call<ArticleList>, throwable: Throwable) {
                if (call.isCanceled) Log.d("initializeArticlesFromApi", throwable.message)
            }

            override fun onResponse(call: Call<ArticleList>, response: Response<ArticleList>) {
                if (response.isSuccessful) {
                    Log.d("initializeArticlesFromApi", response.body().toString())
                    val articleList: ArticleList? = response.body()
                    articles = articleList?.articles as ArrayList<Article>

                    Log.d("initializeArticlesFromApi", "Articles size: ${articles.size}")
                }
                else if (articles.size != 0 && call.isExecuted) {
                    call.cancel()
                }
            }
        })

        NewsActivity

        Log.d("initializeArticlesFromApi", "Shit")

    }
}