package bio.models.three_d.main_menu.common.article

import android.content.Context
import bio.models.three_d.R

object ArticleData {

    private var articlesDescription: Array<String> = arrayOf()
    private var articleTitles: MutableList<Array<String>> = mutableListOf()
    private var items: List<Article>? = null

    fun init (context: Context) {
        articlesDescription = context.resources.getStringArray(R.array.article_bodies)
        if (articleTitles.size != 0) {
            articleTitles.clear()
        }
        articleTitles.add(context.resources.getStringArray(R.array.blocking_article_titles))
        articleTitles.add(context.resources.getStringArray(R.array.detaling_article_title))
        articleTitles.add(context.resources.getStringArray(R.array.tech_article_titles))
        articleTitles.add(context.resources.getStringArray(R.array.textures_article_titles))
        articleTitles.add(context.resources.getStringArray(R.array.supply_article_titles))
    }

    fun getList () : List<Article> {
        if (items.isNullOrEmpty()) {
            items = createList()
        }
        return items as List<Article>
    }

    fun createList (): List<Article> {
        val articleList = arrayListOf<Article>()
        for (themeId in 0..4) {
            val startId = getStartIdOfArticles(themeId)
            val endId = getEndIdOfArticles(themeId)
            val articleTitles = getArticleStringList(themeId)
            for (id in startId..endId) {
                articleList.add(
                    Article(
                        id = "$id",
                        themeId = themeId,
                        title = articleTitles[id - startId],
                        imageSrc = R.drawable.item_image,
                        desc = articlesDescription?.get(themeId) ?: ""
                    )
                )
            }
        }
        return articleList
    }

    fun getByThemeId (themeId: Int): List<Article> {
        val unfilteredItems = getList()
        return unfilteredItems.filter {
            it.themeId == themeId
        }
    }

    fun getById (id: Int): Article {
        return getList().first { it.id == id.toString() }
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

    private fun getArticleStringList (themeId: Int): Array<String> {
        if (themeId in 0..5) {
            return articleTitles[themeId]
        }
        return articleTitles[0]
    }

    fun recreateList(context: Context) {
        init(context)
        items = null
        items = createList()
    }
}