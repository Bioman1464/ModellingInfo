package bio.models.three_d.main_menu.common.article

import bio.models.three_d.common.AdapterClick
import bio.models.three_d.common.recycler.RecyclerItem

data class Article (
    override val id: String?,
    val themeId: Int,
    val title: String,
    var isFavourite: Boolean = false,
    val imageSrc: Int,
    val desc: String = "",
): RecyclerItem, AdapterClick