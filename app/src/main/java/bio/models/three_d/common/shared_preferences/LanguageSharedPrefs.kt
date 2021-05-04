package bio.models.three_d.common.shared_preferences

import android.content.Context
import android.content.SharedPreferences
import bio.models.three_d.common.SingletonHolder
import bio.models.three_d.main_menu.Language

class LanguageSharedPrefs private constructor(context: Context){

    companion object : SingletonHolder<LanguageSharedPrefs, Context>(::LanguageSharedPrefs) {
        lateinit var prefs: SharedPreferences
    }

    init {
        prefs = context.getSharedPreferences("language", Context.MODE_PRIVATE)
    }

    fun setLanguage(languageCode: String) {
        prefs.edit().putString("code", languageCode).apply()
    }

    fun getLanguage(): String {
        return prefs.getString("code", Language.LANGUAGE_RUSSIAN)!!
    }

}