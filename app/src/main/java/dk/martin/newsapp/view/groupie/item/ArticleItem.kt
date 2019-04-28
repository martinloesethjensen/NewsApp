package dk.martin.newsapp.view.groupie.item

import android.content.Intent
import android.util.Log
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import dk.martin.newsapp.R
import dk.martin.newsapp.model.Article
import dk.martin.newsapp.service.module.GlideApp
import dk.martin.newsapp.service.utils.ARTICLE_URL
import dk.martin.newsapp.view.ui.WebViewActivity
import kotlinx.android.synthetic.main.article_not_list.*

class ArticleItem(private val article: Article) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        Log.d("ArticleItem", "Article title: ${article.title}")

        GlideApp.with(viewHolder.itemView)
            .load(article.urlToImage)
            .into(viewHolder.card_view_image!!)
        viewHolder.card_view_image_title.text = article.title
        viewHolder.card_view_image_description.text = article.description
        viewHolder.adapterPosition

        viewHolder.itemView.setOnClickListener {
            val intent = Intent(
                it.context,
                WebViewActivity::class.java
            )

            intent.putExtra(ARTICLE_URL, article.url)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            it.context.startActivity(intent)
        }
    }

    override fun getLayout(): Int {
        return R.layout.article_not_list
    }
}