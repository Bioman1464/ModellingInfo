package bio.models.three_d.main_menu.common.article

import bio.models.three_d.R
import bio.models.three_d.common.RecyclerItem

object ArticleData {

    fun createList(): List<RecyclerItem> {
        val list = ArrayList<RecyclerItem>()
        list.add(
            Article(
                id = "1",
                title = "Low-poly",
                imageSrc = R.drawable.item_image
            )
        )

        list.add(
            Article(
                id = "2",
                title = "Topology",
                imageSrc = R.drawable.item_image
            )
        )

        list.add(
            Article(
                id = "3",
                title = "UV-развертка",
                imageSrc = R.drawable.item_image
            )
        )

        list.add(
            Article(
                id = "4",
                title = "High-poly",
                imageSrc = R.drawable.item_image
            )
        )

        list.add(
            Article(
                id = "5",
                title = "Bake",
                imageSrc = R.drawable.item_image
            )
        )

        return list
    }
}