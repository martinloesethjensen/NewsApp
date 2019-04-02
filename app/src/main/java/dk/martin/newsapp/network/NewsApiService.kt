package dk.martin.newsapp.network

import dk.martin.newsapp.model.ArticleList
import io.reactivex.Observable
import retrofit2.http.GET

interface NewsApiService {
    @GET("everything?q=Adapt agencies&from=2019-03-31&sortBy=publishedAt&apiKey=4d4f2a2fa20a4804ae85565acdad648e")
    fun getArticles(): Observable<ArticleList>
}