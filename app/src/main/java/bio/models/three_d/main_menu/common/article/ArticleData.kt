package bio.models.three_d.main_menu.common.article

import bio.models.three_d.R
import bio.models.three_d.common.RecyclerItem

object ArticleData {

    fun getTitle(index: Int): String {
        return when(index) {
            in 0..5 -> "Low-poly"
            in 5..10 -> "Topology"
            in 10..15 -> "Uv-развертка"
            in 15..20 -> "High-poly"
            in 20..25 -> "Bake"
            else -> "Low-poly"
        }
    }

    fun getThemeId(index: Int): Int {
        return when(index) {
            in 0..5 -> 0
            in 5..10 -> 1
            in 10..15 -> 2
            in 15..20 -> 3
            in 20..25 -> 4
            else -> 1
        }
    }

    fun createList(): List<RecyclerItem> {
        val list = ArrayList<RecyclerItem>()
        for (i in 0..25) {
            list.add(
                Article(
                    id = "$i",
                    themeId = getThemeId(i),
                    title = "${getTitle(i)}${i}",
                    imageSrc = R.drawable.item_image
                )
            )
        }
        return list
    }
}