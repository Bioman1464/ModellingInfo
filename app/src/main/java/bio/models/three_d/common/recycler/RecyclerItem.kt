package bio.models.three_d.common.recycler

interface RecyclerItem {
    val id: String?
    override fun equals(other: Any?): Boolean
}