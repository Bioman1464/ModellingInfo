package bio.models.three_d.main_menu.home.theme

import android.content.Context
import bio.models.three_d.R
import bio.models.three_d.common.RecyclerItem

object ThemeData {

    fun getTitleById(context:Context, id: Int): String {
        val stringList = context.resources.getStringArray(R.array.theme_titles)
        return stringList[id%stringList.size]
    }

    fun createList(context: Context): List<RecyclerItem> {
        val stringList = context.resources.getStringArray(R.array.theme_titles)
        val picList = listOf(
            R.drawable.ic_cubes,
            R.drawable.ic_details,
            R.drawable.ic_gears,
            R.drawable.ic_brush,
            R.drawable.ic_section
        )
        val list = ArrayList<RecyclerItem>()
        for(item in stringList.indices) {
            list.add(
                Theme(
                    id = item.toString(),
                    theme = stringList[item%stringList.size],
                    imageSrc = picList[item%picList.size]
                )
            )
        }
        return list
    }

}