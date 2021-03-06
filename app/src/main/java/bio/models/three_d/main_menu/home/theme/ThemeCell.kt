package bio.models.three_d.main_menu.home.theme

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bio.models.three_d.R
import bio.models.three_d.common.AdapterListener
import bio.models.three_d.common.recycler.Cell
import bio.models.three_d.common.recycler.RecyclerItem
import bio.models.three_d.databinding.ThemeItemBinding

object ThemeCell: Cell<RecyclerItem>() {

    override fun belongsTo(item: RecyclerItem?): Boolean {
        return item is Theme
    }

    override fun type(): Int {
        return R.layout.theme_item
    }

    override fun holder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ThemeItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ThemeViewHolder(binding)
    }

    override fun bind(
        holder: RecyclerView.ViewHolder,
        item: RecyclerItem?,
        listener: AdapterListener?
    ) {
        if (holder is ThemeViewHolder && item is Theme) {
            holder.bind(item)
            holder.itemView.setOnClickListener {
                listener?.listen(item, holder.bindingAdapterPosition)
            }
        }
    }
}