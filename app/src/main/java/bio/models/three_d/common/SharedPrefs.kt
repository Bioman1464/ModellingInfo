package bio.models.three_d.common

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPrefs private constructor(context: Context) {

    companion object : SingletonHolder<SharedPrefs, Context>(::SharedPrefs) {
        lateinit var prefs: SharedPreferences
        var favouriteArticles: ArrayList<Article>? = null
        lateinit var gson: Gson
    }

    init {
        prefs = context.getSharedPreferences("articles", Context.MODE_PRIVATE)
        gson = Gson()
    }

    fun checkArticleFavourite(article: Article): Boolean {
        if (favouriteArticles.isNullOrEmpty()) {
            retrieveFavouriteArticleList()
        }
        Log.d("checkArticleFavourite", favouriteArticles?.size.toString())
        favouriteArticles?.forEach {
            if (it.id == article.id) {
                return true
            }
        }
        return false
    }

    fun unfavouriteArticle(article: Article): Boolean {
        val currentArticleList = retrieveFavouriteArticleList()
        Log.d("unfavouriteArticle", currentArticleList.size.toString())
        if (currentArticleList.contains(article)) {
            favouriteArticles?.remove(article)
            Log.d("unfavouriteArticle", favouriteArticles?.size.toString())
            return saveFavouriteArticleList()
        }
        return false
    }

    fun setFavouriteArticle(article: Article): Boolean? {
        if (checkArticleFavourite(article)) {
            Log.d("setFavouriteArticle", "Already favourite")
            return null
        }
        val currentArticleList = retrieveFavouriteArticleList()
        Log.d("setFavouriteArticle", currentArticleList.size.toString())
        currentArticleList.add(article)
        Log.d("setFavouriteArticle", currentArticleList.size.toString())
        return prefs.edit()
            .putString("article_favourite_list", gson.toJson(currentArticleList))
            .commit()
    }

    fun saveFavouriteArticleList(): Boolean {
        return prefs.edit()
            .putString("article_favourite_list", gson.toJson(favouriteArticles))
            .commit()
    }

    fun retrieveFavouriteArticleList(): ArrayList<Article> {
        val articleListRaw = prefs.getString("article_favourite_list", null)
            ?: return arrayListOf()
        val articleList: ArrayList<Article> = gson.fromJson(
            articleListRaw,
            object : TypeToken<List<Article>>() {}.type
        )
        favouriteArticles = articleList
        return favouriteArticles as ArrayList<Article>
    }
}

data class Article(var id: Int, var themeId: Int)