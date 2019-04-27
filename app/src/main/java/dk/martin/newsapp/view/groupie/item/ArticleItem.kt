package dk.martin.newsapp.view.groupie.item

import android.util.Log
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import dk.martin.newsapp.R
import dk.martin.newsapp.model.Article
import dk.martin.newsapp.service.module.GlideApp
import kotlinx.android.synthetic.main.article_not_list.*

class ArticleItem(private val article: Article) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        Log.d("OnBindViewHolder", "${article.title}")

        GlideApp.with(viewHolder.itemView)
            .load(article.urlToImage)
            .into(viewHolder.card_view_image!!)
        viewHolder.card_view_image_title.text = article.title
        viewHolder.card_view_image_description.text = article.description
        viewHolder.adapterPosition
    }

    override fun getLayout(): Int {
        return R.layout.article_not_list
    }
}