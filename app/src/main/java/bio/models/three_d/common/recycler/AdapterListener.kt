package bio.models.three_d.common

interface AdapterListener {
    fun listen(click: AdapterClick?, position: Int)
}
interface AdapterClick