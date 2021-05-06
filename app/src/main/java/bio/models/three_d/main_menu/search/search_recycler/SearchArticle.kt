package bio.models.three_d.main_menu.search.search_recycler

import bio.models.three_d.common.AdapterClick
import bio.models.three_d.common.recycler.RecyclerItem

data class SearchArticle(
    override val id: String?,
    val title: String,
    val articlePart: String,
    val imageSrc: Int? = null
): RecyclerItem, AdapterClick