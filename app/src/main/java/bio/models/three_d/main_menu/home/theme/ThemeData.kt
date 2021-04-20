package bio.models.three_d.main_menu.home.theme

import android.content.Context
import bio.models.three_d.R

object ThemeData {

    private var items: List<Theme>? = null

    fun getList(context: Context): List<Theme> {
        if (items.isNullOrEmpty()) {
            items = createList(context)
        }
        return items as List<Theme>
    }

    fun getTitleById(context:Context, id: Int): String? {
        getList(context)
        return items?.get(id)?.theme
    }

    fun getById(context: Context, id: Int) : Theme? {
        getList(context)
        return items?.get(id)
    }

    fun createList(context: Context): List<Theme> {
        val stringList = context.resources.getStringArray(R.array.theme_titles)
        val picList = listOf(
            R.drawable.ic_cubes,
            R.drawable.ic_details,
            R.drawable.ic_gears,
            R.drawable.ic_brush,
            R.drawable.ic_section
        )
        val list = ArrayList<Theme>()
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