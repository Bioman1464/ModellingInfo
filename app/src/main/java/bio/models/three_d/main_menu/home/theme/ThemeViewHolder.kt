package bio.models.three_d.main_menu.home.theme

import androidx.recyclerview.widget.RecyclerView
import bio.models.three_d.databinding.ThemeItemBinding

class ThemeViewHolder(var view: ThemeItemBinding) : RecyclerView.ViewHolder(view.root) {

    fun bind(theme: Theme) {
        view.themeTitle.text = theme.theme
        view.themeImage.setImageResource(theme.imageSrc)
    }

}