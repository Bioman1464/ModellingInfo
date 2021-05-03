package bio.models.three_d.main_menu.common.article

import android.content.Context
import bio.models.three_d.R
import bio.models.three_d.common.RecyclerItem

object ArticleData {

    private var items: List<Article>? = null

    fun getList(context: Context): List<Article> {
        if (items.isNullOrEmpty()) {
            items = getAll(context)
        }
        return items as List<Article>
    }

    fun getAll(context: Context): List<Article> {
        val descrStringArray = context.resources.getStringArray(R.array.tech_article_bodies)
        val articleList = arrayListOf<Article>()
        for (themeId in 0..4) {
            val startId = getStartIdOfArticles(themeId)
            val endId = getEndIdOfArticles(themeId)
            val articleTitles = getArticleStringList(context, themeId)
            for (id in startId..endId) {
                articleList.add(
                    Article(
                        id = "$id",
                        themeId = themeId,
                        title = articleTitles[id - startId],
                        imageSrc = R.drawable.item_image,
                        desc = descrStringArray[themeId]
                    )
                )
            }
        }
        return articleList
    }

    fun getByThemeId(context: Context, themeId: Int): List<Article> {
        val unfilteredItems = getList(context)
        return unfilteredItems.filter {
            it.themeId == themeId
        }
    }

    fun getById(context: Context, id: Int): Article {
        return getList(context).first { it.id == id.toString() }
    }

    private fun getStartIdOfArticles (themeId: Int): Int {
        return when (themeId) {
            0 -> 0
            1 -> 5
            2 -> 9
            3 -> 14
            4 -> 16
            else -> 0
        }
    }

    private fun getEndIdOfArticles (themeId: Int): Int {
        return when (themeId) {
            0 -> 4
            1 -> 8
            2 -> 13
            3 -> 15
            4 -> 21
            else -> 4
        }
    }

    private fun getArticleStringList (context: Context, themeId: Int): Array<String> {
        return when (themeId) {
            1 -> context.resources.getStringArray(R.array.detaling_article_title)
            2 -> context.resources.getStringArray(R.array.tech_article_titles)
            3 -> context.resources.getStringArray(R.array.textures_article_titles)
            4 -> context.resources.getStringArray(R.array.supply_article_titles)
            else -> context.resources.getStringArray(R.array.blocking_article_titles)
        }
    }
}