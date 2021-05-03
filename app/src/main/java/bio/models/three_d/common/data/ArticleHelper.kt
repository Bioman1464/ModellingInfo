package bio.models.three_d.common.data

import android.util.Log

object ArticleHelper {

    private val TAG = this::class.java.simpleName

    fun getIdsListFromArticles(articles: List<Article>): ArrayList<Int> {
        val articleIds = arrayListOf<Int>()
        if (articles.isEmpty()) {
            return articleIds
        }
        for (article in articles) {
            articleIds.add(article.id)
        }
        return articleIds
    }

    fun parseRawArticleIds(rawData: String): List<Article> {
        Log.d(TAG, "RawArticleIds: ${rawData}")
        val ids = rawData.split(",").map { it.trim() }
        val articleList: MutableList<Article> = mutableListOf()
        try {
            ids.forEach { id ->
                articleList.add(Article(id.toInt(), themeIdById(id.toInt())))
            }
        } catch (e: NumberFormatException) {
            Log.d(TAG, "Can't parse")
        }
        Log.d(TAG, "ParsedArticleIds: ${articleList.toString()}")
        return articleList
    }

    fun themeIdById(articleId: Int): Int {
        return when (articleId) {
            in 0..4 -> 0
            in 5..8 -> 1
            in 9..13 -> 2
            in 14..15 -> 3
            in 16..21 -> 4
            else -> 0
        }
    }

    fun orderIdById(articleId: Int): Int {
        return when (articleId) {
            in 0..4 -> articleId - 0
            in 5..8 -> articleId - 5
            in 9..13 -> articleId - 9
            in 14..15 -> articleId - 14
            in 16..21 -> articleId - 16
            else -> 0
        }
    }

}