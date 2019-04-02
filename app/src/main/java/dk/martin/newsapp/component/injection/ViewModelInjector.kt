package dk.martin.newsapp.component.injection

import dagger.Component
import dk.martin.newsapp.module.NetworkModule
import dk.martin.newsapp.ui.article.ArticleListViewModel
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified PostListViewModel.
     * @param  articleListViewModel ArticleListViewModel in which to inject the dependencies
     */
    fun inject(articleListViewModel: ArticleListViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}