package dk.martin.newsapp.api

import dk.martin.newsapp.model.ArticleList
import retrofit2.http.GET
import retrofit2.Call

interface NewsApiService {
    @GET("everything?q=Adapt agencies&from=2019-03-31&sortBy=publishedAt&apiKey=4d4f2a2fa20a4804ae85565acdad648e")
    fun getArticles() : Call<ArticleList>
}