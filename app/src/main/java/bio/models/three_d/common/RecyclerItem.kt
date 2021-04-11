package bio.models.three_d.common

interface RecyclerItem {
    val id: String?
    override fun equals(other: Any?): Boolean
}