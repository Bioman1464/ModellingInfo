package bio.models.three_d.main_menu.home.theme

import bio.models.three_d.R
import bio.models.three_d.common.RecyclerItem

object ThemeData {

    fun createList(): List<RecyclerItem> {
        val list = ArrayList<RecyclerItem>()
        list.add(
            Theme(
            id = "1",
            theme = "Theme",
            imageSrc = R.drawable.item_image
        )
        )

        list.add(
            Theme(
            id = "2",
            theme = "Theme1",
            imageSrc = R.drawable.item_image
        )
        )

        list.add(
            Theme(
            id = "3",
            theme = "Theme2",
            imageSrc = R.drawable.item_image
        )
        )

        list.add(
            Theme(
            id = "4",
            theme = "Theme3",
            imageSrc = R.drawable.item_image
        )
        )

        list.add(
            Theme(
            id = "5",
            theme = "Theme4",
            imageSrc = R.drawable.item_image
        )
        )

        return list
    }

}