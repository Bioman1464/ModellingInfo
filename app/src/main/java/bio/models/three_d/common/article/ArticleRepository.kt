package bio.models.three_d.common.article

import bio.models.three_d.common.ArticleSharedPrefs
import bio.models.three_d.common.UserAccount
import bio.models.three_d.common.data.Article
import bio.models.three_d.common.data.ArticleHelper
import bio.models.three_d.common.firebase.data.FirebaseDataHelper
import com.google.firebase.database.DatabaseReference

class ArticleRepository(
    private val sharedPrefs: ArticleSharedPrefs,
    private val databaseReference: DatabaseReference) {

    fun addArticleToFavourite(article: Article): Boolean? {
        if (UserAccount.uid.isNotEmpty()) {
            FirebaseDataHelper.saveUsersFavourite(
                getFormattedIds("add", article.id),
                databaseReference
            )
        }
        return sharedPrefs.setFavouriteArticle(article)
    }

    fun removeArticleFromFavourite(article: Article): Boolean {
        val localResponse = sharedPrefs.unfavouriteArticle(article)
        if (UserAccount.uid.isNotEmpty()) {
            FirebaseDataHelper.saveUsersFavourite(
                getFormattedIds("remove", article.id),
                databaseReference
            )
        }
        return localResponse
    }

    private fun getNewIdsList(action: String, articleId: Int): ArrayList<Int> {
        val idsList = ArticleHelper.getIdsListFromArticles(
            sharedPrefs.retrieveFavouriteArticleList()
        )
        if (idsList.contains(articleId)) {
            return idsList
        }
        when(action) {
            "remove" -> idsList.remove(articleId)
            "add" -> idsList.add(articleId)
        }
        return idsList
    }

    private fun getFormattedIds(action: String, id: Int): String {
        val newFavouriteIdsList = getNewIdsList(action, id)
        val regex = """[\[\]]""".toRegex()
        return regex.replace(newFavouriteIdsList.toString(), "")
    }

}