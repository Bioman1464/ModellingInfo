package bio.models.three_d.main_menu.search.search_recycler

import bio.models.three_d.databinding.ArticleSearchItemBinding
import androidx.recyclerview.widget.RecyclerView

class SearchArticleViewHolder(val view: ArticleSearchItemBinding) :
    RecyclerView.ViewHolder(view.root) {
    fun bind(searchArticle: SearchArticle) {
        view.articleTitle.text = searchArticle.title
        view.articleSearchedText.text = searchArticle.articlePart
    }
}