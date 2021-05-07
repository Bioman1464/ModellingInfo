package bio.models.three_d.main_menu.home.theme

import android.content.Context
import android.content.SharedPreferences
import bio.models.three_d.R
import bio.models.three_d.common.SingletonHolder
import bio.models.three_d.common.data.Article
import bio.models.three_d.common.shared_preferences.ArticleSharedPrefs
import com.google.gson.Gson

object ThemeData {

    private var themeTitles: Array<String> = arrayOf()
    private var items: List<Theme>? = null

    fun init (context: Context) {
        themeTitles = context.resources.getStringArray(R.array.theme_titles)
    }

    fun getList(): List<Theme> {
        if (items.isNullOrEmpty()) {
            items = createList()
        }
        return items as List<Theme>
    }

    fun getTitleById(id: Int): String? {
        getList()
        return items?.get(id)?.theme
    }

    fun getById(id: Int) : Theme? {
        getList()
        return items?.get(id)
    }

    private fun createList(): List<Theme> {
        val picList = listOf(
            R.drawable.ic_cubes,
            R.drawable.ic_details,
            R.drawable.ic_gears,
            R.drawable.ic_brush,
            R.drawable.ic_section
        )
        val list = ArrayList<Theme>()
        for(item in themeTitles.indices) {
            list.add(
                Theme(
                    id = item.toString(),
                    theme = themeTitles[item% themeTitles.size],
                    imageSrc = picList[item%picList.size]
                )
            )
        }
        return list
    }

    fun recreateList(context: Context) {
        init(context)
        items = null
        items = createList()
    }

}