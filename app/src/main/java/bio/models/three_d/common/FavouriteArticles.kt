package bio.models.three_d.common

import android.content.SharedPreferences
import com.google.gson.Gson

data class FavouriteArticles(var ids: ArrayList<Int>) {}

object FavouriteStorageHelper {

    lateinit var sharedPrefs: SharedPreferences

    fun setPrefs(sharedPrefs: SharedPreferences) {
        this.sharedPrefs = sharedPrefs
    }

    fun saveToStorage(favouriteIds: List<Int>) {
        var article: ArrayList<Int> =  arrayListOf()

        val lastSaved = getFromStorage()
        for (x in lastSaved) {
            if (!favouriteIds.contains(x)) lastSaved.add(x)
        }

        val prefsEditor: SharedPreferences.Editor = sharedPrefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(article);
        prefsEditor.putString("favourite_articles", json);
        prefsEditor.apply();
    }

    fun getFromStorage(): ArrayList<Int> {
        val gson = Gson()
        val json: String = sharedPrefs.getString("favourite_articles", "").toString()
        val obj: FavouriteArticles = gson.fromJson(json, FavouriteArticles::class.java)
        return obj.ids
    }

}
