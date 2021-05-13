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
        articleTitles.add(context.resources.getStringArray(R.array.article_titles_1))
        articleTitles.add(context.resources.getStringArray(R.array.article_titles_2))
        articleTitles.add(context.resources.getStringArray(R.array.article_titles_3))
        articleTitles.add(context.resources.getStringArray(R.array.article_titles_4))
        articleTitles.add(context.resources.getStringArray(R.array.article_titles_5))
    }

    fun getList () : List<Article> {
        if (items.isNullOrEmpty()) {
            items = createList()
        }
        return items as List<Article>
    }

    private fun createList (): List<Article> {
        val articleList = arrayListOf<Article>()
        for (themeId in 0..articleTitles.size) {
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
                        desc = getArticleDescription(id)
                    )
                )
            }
        }
        return articleList
    }

    fun getArticleDescription(articleId: Int): String {
        if (articleId in articlesDescription.indices) {
            return articlesDescription[articleId] ?: ""
        }
        return ""
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
            1 -> 7
            2 -> 11
            3 -> 16
            4 -> 22
            else -> 0
        }
    }

    private fun getEndIdOfArticles (themeId: Int): Int {
        return when (themeId) {
            0 -> 6
            1 -> 10
            2 -> 15
            3 -> 21
            4 -> 23
            else -> 6
        }
    }

    private fun getArticleStringList (themeId: Int): Array<String> {
        if (themeId in 0..4) {
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