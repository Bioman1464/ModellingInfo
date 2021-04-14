package bio.models.three_d.main_menu.common.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bio.models.three_d.R
import bio.models.three_d.common.AdapterListener
import bio.models.three_d.common.Cell
import bio.models.three_d.common.RecyclerItem
import bio.models.three_d.databinding.ArticleItemBinding

object ArticleCell: Cell<RecyclerItem>() {
    override fun belongsTo(item: RecyclerItem?): Boolean {
        return item is Article
    }

    override fun type(): Int {
        return R.layout.article_item
    }

    override fun holder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ArticleItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun bind(
        holder: RecyclerView.ViewHolder,
        item: RecyclerItem?,
        listener: AdapterListener?
    ) {
        if (holder is ArticleViewHolder && item is Article) {
            holder.bind(item)
            holder.itemView.setOnClickListener {
                listener?.listen(item, holder.bindingAdapterPosition)
            }
            holder.view.favourite.setOnClickListener {
                val changedItem = item
                changedItem.isFavourite  = !item.isFavourite
                if (changedItem.isFavourite) {
                    holder.view.favourite.setImageResource(R.drawable.ic_star_white_filled)
                } else {
                    holder.view.favourite.setImageResource(R.drawable.ic_star_white)
                }
                listener?.listen(changedItem, holder.bindingAdapterPosition)
            }
        }
    }
}