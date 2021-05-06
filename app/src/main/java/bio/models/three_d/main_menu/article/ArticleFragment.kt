package bio.models.three_d.main_menu.article

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import bio.models.three_d.R
import bio.models.three_d.common.data.Article
import bio.models.three_d.common.shared_preferences.ArticleSharedPrefs
import bio.models.three_d.common.UserAccount
import bio.models.three_d.common.article.ArticleRepository
import bio.models.three_d.common.data.ArticleHelper
import bio.models.three_d.common.firebase.data.FirebaseDataHelper
import bio.models.three_d.common.shared_preferences.LanguageSharedPrefs
import bio.models.three_d.databinding.FragmentArticleBinding
import bio.models.three_d.main_menu.common.article.ArticleData
import bio.models.three_d.main_menu.home.theme.ThemeData
import com.yariksoffice.lingver.Lingver

class ArticleFragment : Fragment(R.layout.fragment_article) {

    private val TAG = this::class.java.simpleName
    private lateinit var navArgs: ArticleFragmentArgs
    private lateinit var binding: FragmentArticleBinding
    private lateinit var prefsArticle: ArticleSharedPrefs
    private lateinit var article: Article

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navArgs = ArticleFragmentArgs.fromBundle(requireArguments())
        binding = FragmentArticleBinding.bind(view)
        prefsArticle = ArticleSharedPrefs.getInstance(requireContext())
        showArticleData()
        initView()
        setLocale()
    }

    private fun setLocale() {
        val savedLanguage = LanguageSharedPrefs.getInstance(requireContext()).getLanguage()
        Lingver.getInstance().setLocale(requireContext(), savedLanguage)
    }

    private fun initView() {
        binding.addToFavourite.setOnClickListener {
            changeFavouriteState()
        }
    }

    private fun showArticleData() {
        val articleId = navArgs.articleId
        articleId.let {
            showArticle()
            val article = ArticleData.getById(requireContext(), it)
            val theme = ThemeData.getById(requireContext(), article.themeId)
            this.article = Article(articleId, theme?.id?.toInt() ?: 0)
            updateHeader(theme?.theme ?: "", article.title)
            checkIfFavourite()
        }
    }

    private fun updateHeader(themeTitle: String, articleTitle: String) {
        binding.articleTitle.text = articleTitle
        binding.themeTitle.text = themeTitle
    }

    private fun checkIfFavourite() {
        val isFavourite = prefsArticle.checkArticleFavourite(article)
        Log.d("checkIfFavourite", isFavourite.toString())
        changeArticleStartImage(isFavourite)
    }

    private fun changeArticleStartImage(filled: Boolean) {
        if (filled) {
            binding.addToFavourite.setImageResource(R.drawable.ic_star_blue)
            return
        }
        binding.addToFavourite.setImageResource(R.drawable.ic_star_blue_outline)
    }

    private fun changeFavouriteState() {
        val isFavourite: Boolean = prefsArticle.checkArticleFavourite(article)
        changeArticleStartImage(!isFavourite)
        if (UserAccount.uid.isEmpty()) {
            Log.d(TAG, "Update shared prefs")
            updateDatabaseData(isFavourite)
            return
        }
        Log.d(TAG, "Checking if firebase updated")
        updateLocalData(isFavourite)
    }

    private fun checkForUpdates(isFavourite: Boolean, articles: List<Article>): Boolean {
        if (isFavourite) {
            if (articles.contains(article)) {
                return true
            }
            return false
        }
        if (articles.contains(article)) {
            return false
        }
        return true
    }

    private fun updateLocalData(isFavourite: Boolean) {
        val task = FirebaseDataHelper
            .getUserFavouriteReference(requireContext(), UserAccount.uid)
            .get()

        task.addOnCompleteListener {
           if (it.isSuccessful) {
               val articles = ArticleHelper.parseRawArticleIds(it.result!!.value.toString())
               val needUpdate = checkForUpdates(isFavourite, articles)
               if (needUpdate) {
                   Log.d(TAG, "Update ")
                   updateDatabaseData(isFavourite)
                   return@addOnCompleteListener
               }
               Log.d(TAG, "Update only shared prefs")
               updateSharedPrefs(articles)
           }
        }
    }

    private fun updateSharedPrefs(articles: List<Article>) {
        val sharedPreferences = ArticleSharedPrefs.getInstance(requireContext())
        sharedPreferences.reloadFavouriteArticleList(articles)
    }

    private fun updateDatabaseData(isFavourite: Boolean) {
        val articleRepository = ArticleRepository(
            ArticleSharedPrefs.getInstance(requireContext()),
            FirebaseDataHelper.getUserFavouriteReference(requireContext(), UserAccount.uid)
        )
        val prefsResponse: Boolean? = if (isFavourite) {
            articleRepository.removeArticleFromFavourite(article)
        } else {
            articleRepository.addArticleToFavourite(article)
        }
        if (prefsResponse == null) {
            Toast.makeText(requireContext(), "Ошибка записи в базу данных", Toast.LENGTH_SHORT)
                .show()
            changeArticleStartImage(prefsArticle.checkArticleFavourite(article))
        }
    }

    private fun showArticle() {
        //TODO:: get article file by id
        showArticleFromHtml("myfile.html")
    }

    private fun showArticleFromHtml(fileName: String) {
        binding.articleWebView.run {
            settings.javaScriptEnabled = true
            setBackgroundColor(Color.TRANSPARENT)
            loadUrl("file:///android_asset/$fileName")
        }
    }
}