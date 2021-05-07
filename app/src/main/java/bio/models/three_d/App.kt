package bio.models.three_d

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import bio.models.three_d.common.shared_preferences.ThemeSharedPrefs
import bio.models.three_d.common.shared_preferences.LanguageSharedPrefs
import bio.models.three_d.main_menu.common.article.ArticleData
import bio.models.three_d.main_menu.home.theme.ThemeData
import com.yariksoffice.lingver.Lingver
import com.yariksoffice.lingver.store.PreferenceLocaleStore
import java.util.*

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        configureTheme()
        configureLanguage()
        initDataObjects()
    }

    private fun configureLanguage() {
        val savedLanguage = LanguageSharedPrefs.getInstance(this).getLanguage()
        val store = PreferenceLocaleStore(this, Locale(savedLanguage))
        val lingver = Lingver.init(this, store)
    }

    private fun configureTheme() {
        val sharedPrefs = ThemeSharedPrefs.getInstance(this)
        when (sharedPrefs.getTheme()) {
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                return
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                return
            }
        }
    }

    private fun initDataObjects() {
        ThemeData.init(this)
        ArticleData.init(this)
    }
}

object Language {
    const val LANGUAGE_ENGLISH = "en"
    const val ENGLISH_COUNTRY = "US"
    const val LANGUAGE_RUSSIAN = "ru"
    const val RUSSIAN_COUNTRY = "RU"
}