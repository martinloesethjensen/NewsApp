package dk.martin.newsapp.service.network

import dk.martin.newsapp.model.ArticleList
import dk.martin.newsapp.service.utils.API_KEY
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("everything?from=2019-03-31&sortBy=publishedAt&apiKey=$API_KEY")
    fun getArticles(@Query("q") query: String): Observable<ArticleList>
}