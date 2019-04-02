package dk.martin.newsapp.ui.article

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import dk.martin.newsapp.R
import dk.martin.newsapp.databinding.ItemArticleBinding
import dk.martin.newsapp.model.Article
import dk.martin.newsapp.model.ArticleList

class ArticleListAdapter : RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {
    private lateinit var articleList: ArticleList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleListAdapter.ViewHolder {
        val binding: ItemArticleBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_article, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleListAdapter.ViewHolder, position: Int) {
        holder.bind(articleList.articles?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (::articleList.isInitialized) articleList.articles?.size!! else 0
    }

    fun updateArticleList(articleList: ArticleList) {
        this.articleList = articleList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = ArticleViewModel()

        fun bind(article: Article) {
            viewModel.bind(article)
            binding.viewModel = viewModel
        }
    }
}