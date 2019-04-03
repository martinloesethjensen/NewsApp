package dk.martin.newsapp.ui.article

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import dk.martin.newsapp.R
import dk.martin.newsapp.model.Article
import dk.martin.newsapp.module.GlideApp
import dk.martin.newsapp.utils.ARTICLE_URL

class ArticleRecyclerAdapter(var context: Context, private val articles: List<Article>) :
    RecyclerView.Adapter<ArticleRecyclerAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.article_not_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ArticleRecyclerAdapter.ViewHolder, position: Int) {
        val article = articles[position]
        Log.d("OnBindViewHolder", "${article.title}")

        GlideApp.with(holder.itemView)
            .load(article.urlToImage)
            .into(holder.image!!)
        holder.textTitle?.text = article.title
        holder.textDescription?.text = article.description
        holder.articlePosition = position
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val image = itemView?.findViewById<ImageView?>(R.id.card_view_image)
        val textTitle = itemView?.findViewById<TextView?>(R.id.card_view_image_title)
        val textDescription = itemView?.findViewById<TextView?>(R.id.card_view_image_description)
        var articlePosition = 0

        init {
            itemView?.setOnClickListener {
                val intent = Intent(
                    context,
                    WebViewActivity::class.java
                )

                Log.d(
                    "ArticleRecyclerAdapter", "Article title: ${articles[articlePosition].title} " +
                            "\nArticle url: ${articles[articlePosition].url}"
                )

                intent.putExtra(ARTICLE_URL, articles[articlePosition].url)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        }
    }
}