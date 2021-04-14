package bio.models.three_d.main_menu.common.article

import androidx.recyclerview.widget.RecyclerView
import bio.models.three_d.databinding.ArticleItemBinding

class ArticleViewHolder(val view: ArticleItemBinding) : RecyclerView.ViewHolder(view.root) {

    fun bind(article: Article) {
        view.articleTitle.text = article.title
        view.articleImage.setImageResource(article.imageSrc)
    }

}