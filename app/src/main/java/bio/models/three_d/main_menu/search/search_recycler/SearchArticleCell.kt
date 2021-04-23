package bio.models.three_d.main_menu.search.search_recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bio.models.three_d.R
import bio.models.three_d.common.AdapterListener
import bio.models.three_d.common.Cell
import bio.models.three_d.common.RecyclerItem
import bio.models.three_d.databinding.ArticleItemBinding
import bio.models.three_d.databinding.ArticleSearchItemBinding
import bio.models.three_d.main_menu.common.article.Article
import bio.models.three_d.main_menu.common.article.ArticleViewHolder

object SearchArticleCell: Cell<RecyclerItem>() {
    override fun belongsTo(item: RecyclerItem?): Boolean {
        return item is SearchArticle
    }

    override fun type(): Int {
        return R.layout.article_search_item
    }

    override fun holder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ArticleSearchItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchArticleViewHolder(binding)
    }

    override fun bind(
        holder: RecyclerView.ViewHolder,
        item: RecyclerItem?,
        listener: AdapterListener?
    ) {
        if (holder is SearchArticleViewHolder && item is SearchArticle) {
            holder.bind(item)
            holder.itemView.setOnClickListener {
                Log.d("TEST CLICK", "${holder.bindingAdapterPosition}")
                listener?.listen(item, holder.bindingAdapterPosition)
            }
        }
    }
}