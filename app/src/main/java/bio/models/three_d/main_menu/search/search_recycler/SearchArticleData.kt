package bio.models.three_d.main_menu.search.search_recycler

import android.util.Log
import bio.models.three_d.main_menu.common.article.Article

object SearchArticleData {

    fun filterArticleItems(charSequence: CharSequence, articleList: List<Article>): List<SearchArticle> {
        val searchList = arrayListOf<SearchArticle>()
        val filteredArticleList = filterByContent(charSequence.toString(), articleList)
        for (article in filteredArticleList) {
            searchList.add(
                SearchArticle(
                    id = article.id,
                    title = article.title,
                    articlePart = getPartFromArticleContent(charSequence.toString(), article.desc)
                )
            )
        }
        return searchList
    }

    private fun filterByContent(searchInput: String, unfilteredArticles: List<Article>): List<Article> {
        val filteredArticles = arrayListOf<Article>()
        for (article in unfilteredArticles) {
            if (article.desc.contains(searchInput, true)) {
                filteredArticles.add(article)
            }
        }
        return filteredArticles
    }

    private fun getPartFromArticleContent(searchInput: String, articleContent: String): String {
        if (articleContent.contains(searchInput, true)) {
            var startIndex = articleContent.indexOf(searchInput, ignoreCase = true)
            var endIndex = startIndex + searchInput.length
            while ((endIndex - startIndex) < 24 && (startIndex > 0 || endIndex < articleContent.length)) {
                if (startIndex > 0) {
                    startIndex-=1
                }
                if (endIndex < articleContent.length) {
                    endIndex+=1
                }
            }
            val contentPart = articleContent.subSequence(startIndex, endIndex)
            return (if(startIndex == 0) "" else "... ") +
                    "$contentPart" +
                    if(endIndex == articleContent.length)  "" else " ..."
        }
        return "NOTHING"
    }

    /*private fun getPartFromArticleContent(searchInput: String, articleContent: String): String {
        var index = articleContent.indexOf(searchInput)
        var indexStart = index
        var indexEnd = index
        if (index > 0
            && articleContent[index] != ' '
            && articleContent[index] != '.'
            && articleContent[index] != ','
            && articleContent[index] != ':'
            && articleContent[index] != ';') {

            while (index >= 0) {
                if (index > 0 && articleContent[index] != ' '
                    && articleContent[index] != '.'
                    && articleContent[index] != ','
                    && articleContent[index] != ':'
                    && articleContent[index] != ';') {
                    index -= 1
                    break
                }
            }
        }

        indexStart = index + 1
        if (index > 0) {
            indexStart = index
        }

        index = indexEnd

        if ()

            while (index > 0 && articleContent[index] != ' '
                && articleContent[index] != '.'
                && articleContent[index] != ','
                && articleContent[index] != ':'
                && articleContent[index] != ';') {
                if (index > 0




                ) {
                }
            }
        return "...  ..."
    }*/

}