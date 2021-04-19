package bio.models.three_d.main_menu.common.article

import android.content.Context
import bio.models.three_d.R
import bio.models.three_d.common.RecyclerItem

object ArticleData {

    fun getTitle(index: Int): String {
        return when(index) {
            in 0..5 -> "Low-poly"
            in 5..10 -> "Topology"
            in 10..15 -> "Uv-развертка"
            in 15..20 -> "High-poly"
            in 20..25 -> "Bake"
            else -> "Low-poly"
        }
    }

    fun getThemeId(index: Int): Int {
        return when(index) {
            in 0..5 -> 0
            in 5..10 -> 1
            in 10..15 -> 2
            in 15..20 -> 3
            in 20..25 -> 4
            else -> 1
        }
    }

    fun getArticlesByThemeId(context: Context, themeId: Int): List<RecyclerItem> {
        val articleList = arrayListOf<RecyclerItem>()
        var startId = 0
        var endId = 0
        val articleTitles: Array<String> = when (themeId) {
            0 -> {
                startId = 0
                endId = 4
                context.resources.getStringArray(R.array.blocking_article_titles)
            }
            1 -> {
                startId = 5
                endId = 8
                context.resources.getStringArray(R.array.detaling_article_title)
            }
            2 -> {
                startId = 9
                endId = 13
                context.resources.getStringArray(R.array.tech_article_titles)
            }
            3 -> {
                startId = 14
                endId = 15
                context.resources.getStringArray(R.array.textures_article_titles)
            }
            4 -> {
                startId = 16
                endId = 21
                context.resources.getStringArray(R.array.supply_article_titles)
            }
            else -> arrayOf()
        }

        for (id in startId..endId) {
            articleList.add(
                Article(
                    id = "$id",
                    themeId = themeId,
                    title = articleTitles[id - startId],
                    imageSrc = R.drawable.item_image
                )
            )
        }
        return articleList
    }

    fun itemByArticleId (context: Context, articleId: Int): RecyclerItem {
        val themeId = themeIdByArticleId(articleId)
        val articleTitles: Array<String> = when (themeId) {
            0 -> {
                context.resources.getStringArray(R.array.blocking_article_titles)
            }
            1 -> {
                context.resources.getStringArray(R.array.detaling_article_title)
            }
            2 -> {
                context.resources.getStringArray(R.array.tech_article_titles)
            }
            3 -> {
                context.resources.getStringArray(R.array.textures_article_titles)
            }
            4 -> {
                context.resources.getStringArray(R.array.supply_article_titles)
            }
            else -> arrayOf()
        }
        return Article(
            id = "$articleId",
            themeId = getThemeId(articleId),
            title = articleTitles[orderIdByArticleId(articleId)],
            imageSrc = R.drawable.item_image
        )
    }

    fun themeIdByArticleId(id: Int): Int {
        return when (id) {
            in 0..4 -> 0
            in 5..8 -> 1
            in 9..13 -> 2
            in 14..15 -> 3
            in 16..21 -> 4
            else -> 0
        }
    }

    fun orderIdByArticleId(id: Int): Int {
        return when (id) {
            in 0..4 -> id - 0
            in 5..8 -> id - 5
            in 9..13 -> id - 9
            in 14..15 -> id - 14
            in 16..21 -> id - 16
            else -> 0
        }
    }

    fun getArticleById(id: Int): RecyclerItem {
        return Article(
            id = "$id",
            themeId = getThemeId(id),
            title = "${getTitle(id)}${id}",
            imageSrc = R.drawable.item_image
        )
    }

    fun createList(): List<RecyclerItem> {
        val list = ArrayList<RecyclerItem>()
        for (i in 0..25) {
            list.add(
                Article(
                    id = "$i",
                    themeId = getThemeId(i),
                    title = "${getTitle(i)}${i}",
                    imageSrc = R.drawable.item_image
                )
            )
        }
        return list
    }
}