package dk.martin.newsapp.ui.article

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import dk.martin.newsapp.R
import dk.martin.newsapp.databinding.ItemArticleBinding
import dk.martin.newsapp.model.Article
import dk.martin.newsapp.model.ArticleList
import dk.martin.newsapp.module.GlideApp
import dk.martin.newsapp.utils.ARTICLE_URL
import dk.martin.newsapp.view.WebViewActivity

class ArticleListAdapter : RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {
    private lateinit var articleList: ArticleList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleListAdapter.ViewHolder {
        val binding: ItemArticleBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_article, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleListAdapter.ViewHolder, position: Int) {
        holder.bind(articleList.articles?.get(position)!!)
        holder.articlePosition = position
        GlideApp.with(holder.itemView)
            .load(articleList.articles?.get(position)!!.urlToImage)
            .into(holder.image!!)
    }

    override fun getItemCount(): Int {
        return if (::articleList.isInitialized) articleList.articles?.size!! else 0
    }

    fun updateArticleList(articleList: ArticleList) {
        this.articleList = articleList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = ArticleViewModel()
        val image = itemView.findViewById<ImageView?>(R.id.card_view_image)
        var articlePosition = 0

        init {
            itemView.setOnClickListener {
                val intent = Intent(
                    binding.root.context,
                    WebViewActivity::class.java
                )

                Log.d(
                    "ArticleRecyclerAdapter", "Article title: ${articleList.articles?.get(articlePosition)!!.title} " +
                            "\nArticle url: ${articleList.articles?.get(articlePosition)!!.url}"
                )
                intent.putExtra(ARTICLE_URL, articleList.articles?.get(articlePosition)!!.url)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                binding.root.context.startActivity(intent)
            }
        }

        fun bind(article: Article) {
            viewModel.bind(article)
            binding.viewModel = viewModel
        }
    }
}