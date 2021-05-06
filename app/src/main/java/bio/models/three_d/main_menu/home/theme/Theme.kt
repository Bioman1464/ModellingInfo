package bio.models.three_d.main_menu.home.theme

import bio.models.three_d.common.AdapterClick
import bio.models.three_d.common.recycler.RecyclerItem

data class Theme(
    override val id: String,
    val theme: String,
    val imageSrc: Int
): RecyclerItem, AdapterClick