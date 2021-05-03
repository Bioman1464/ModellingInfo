package bio.models.three_d.common.data

object ArticleHelper {

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