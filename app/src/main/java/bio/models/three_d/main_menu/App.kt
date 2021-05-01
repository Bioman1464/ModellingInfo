package bio.models.three_d.main_menu

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import bio.models.three_d.common.ThemeSharedPrefs

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        configureTheme()
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

}