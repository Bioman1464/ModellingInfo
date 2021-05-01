package bio.models.three_d.common

import android.content.Context
import android.content.SharedPreferences

class ThemeSharedPrefs private constructor(context: Context) {

    companion object : SingletonHolder<ThemeSharedPrefs, Context>(::ThemeSharedPrefs) {
        lateinit var prefs: SharedPreferences
        var themeId: Int = 0
    }

    init {
        prefs = context.getSharedPreferences("theme", Context.MODE_PRIVATE)
    }

    fun setTheme(newThemeId: Int) {
        themeId = newThemeId
        prefs.edit().putInt("theme_id", newThemeId).apply()
    }

    fun getTheme() : Int {
        themeId = prefs.getInt("theme_id", 0)
        return themeId
    }

}