package bio.models.three_d.main_menu.home.theme

import bio.models.three_d.R
import bio.models.three_d.common.RecyclerItem

object ThemeData {

    fun createList(): List<RecyclerItem> {
        val list = ArrayList<RecyclerItem>()
        list.add(
            Theme(
            id = "1",
            theme = "Блокинг",
            imageSrc = R.drawable.item_image
        )
        )

        list.add(
            Theme(
            id = "2",
            theme = "Детализация",
            imageSrc = R.drawable.item_image
        )
        )

        list.add(
            Theme(
            id = "3",
            theme = "Технические\nэтапы",
            imageSrc = R.drawable.item_image
        )
        )

        list.add(
            Theme(
            id = "4",
            theme = "Текстуры",
            imageSrc = R.drawable.item_image
        )
        )

        list.add(
            Theme(
            id = "5",
            theme = "Подача",
            imageSrc = R.drawable.item_image
        )
        )

        return list
    }

}