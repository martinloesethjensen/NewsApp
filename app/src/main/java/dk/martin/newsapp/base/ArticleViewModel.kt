package dk.martin.newsapp.base

import android.arch.lifecycle.ViewModel
import dk.martin.newsapp.component.injection.DaggerViewModelInjector
import dk.martin.newsapp.component.injection.ViewModelInjector
import dk.martin.newsapp.module.NetworkModule
import dk.martin.newsapp.ui.article.ArticleListViewModel

abstract class ArticleViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is ArticleListViewModel -> injector.inject(this)
        }
    }
}