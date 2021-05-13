package bio.models.three_d.common.shared_preferences

import android.content.Context
import android.content.SharedPreferences
import bio.models.three_d.common.SingletonHolder

class FontSharedPrefs private constructor(context: Context) {

    val prefsKey = "font_size_key"

    companion object : SingletonHolder<FontSharedPrefs, Context>(::FontSharedPrefs) {
        lateinit var prefs: SharedPreferences
    }

    init {
        prefs = context.getSharedPreferences("font_size", Context.MODE_PRIVATE)
    }

    fun setFontSize(fontKey: FontSize) {
        val stringFontKey = when (fontKey) {
            FontSize.SMALL -> "small"
            FontSize.MEDIUM -> "medium"
            FontSize.BIG -> "big"
            else -> "medium"
        }
        prefs.edit().putString(prefsKey, stringFontKey).apply()
    }

    fun getFontSizeKey(): FontSize {
        val fontSize = prefs.getString(prefsKey, "medium") ?: "medium"
        return when (fontSize) {
            "small" -> FontSize.SMALL
            "medium" -> FontSize.MEDIUM
            "big" -> FontSize.BIG
            else -> FontSize.BIG
        }
    }

    fun getFontSize (): Int {
        val fontSize = getFontSizeKey()
        val dimenFontSize = getFontSizeIdByKey(fontSize)
        return dimenFontSize
    }

    private fun getFontSizeIdByKey(fontKey: FontSize): Int {
        return when (fontKey) {
            FontSize.SMALL -> 12
            FontSize.MEDIUM -> 16
            FontSize.BIG -> 20
        }
    }

}

enum class FontSize {SMALL, MEDIUM, BIG}