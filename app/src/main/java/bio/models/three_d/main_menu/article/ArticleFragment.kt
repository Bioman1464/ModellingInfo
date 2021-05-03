package bio.models.three_d.main_menu.article

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import bio.models.three_d.R
import bio.models.three_d.common.data.Article
import bio.models.three_d.common.ArticleSharedPrefs
import bio.models.three_d.databinding.FragmentArticleBinding
import bio.models.three_d.main_menu.common.article.ArticleData
import bio.models.three_d.main_menu.home.theme.ThemeData

class ArticleFragment : Fragment(R.layout.fragment_article) {

    private lateinit var navArgs: ArticleFragmentArgs
    private lateinit var binding: FragmentArticleBinding
    private lateinit var prefsArticle: ArticleSharedPrefs
    private lateinit var article: Article

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navArgs = ArticleFragmentArgs.fromBundle(requireArguments())
        binding = FragmentArticleBinding.bind(view)
        prefsArticle = ArticleSharedPrefs.getInstance(requireContext())
        setTitle()
        initView()
    }

    private fun initView() {
        binding.addToFavourite.setOnClickListener {
            changeFavourite()
        }
    }

    private fun setTitle() {
        val articleId = navArgs.articleId
        article = Article(articleId, 0)
        articleId.let {
            val article = ArticleData.getById(requireContext(), it)
            val theme = ThemeData.getById(requireContext(), article.themeId)

            binding.articleTitle.text = article.title
            binding.themeTitle.text = theme?.theme

            checkIfFavourite()
            getArticleById()
        }
    }

    private fun checkIfFavourite() {
        val isFavourite = prefsArticle.checkArticleFavourite(article)
        Log.d("checkIfFavourite", isFavourite.toString())
        setFavouriteStar(isFavourite)
    }

    private fun setFavouriteStar(isFavourite: Boolean) {
        if (isFavourite) {
            binding.addToFavourite.setImageResource(R.drawable.ic_star_blue)
            return
        }
        binding.addToFavourite.setImageResource(R.drawable.ic_star_blue_outline)
    }

    private fun changeFavourite() {
        val prefsResponse: Boolean?
        if (prefsArticle.checkArticleFavourite(article)) {
            setFavouriteStar(false)
            prefsResponse = prefsArticle.unfavouriteArticle(article)
        } else {
            setFavouriteStar(true)
            prefsResponse = prefsArticle.setFavouriteArticle(article)
        }
        if (prefsResponse == null) {
            Toast.makeText(requireContext(), "Ошибка записи в базу данных", Toast.LENGTH_SHORT)
                .show()
            setFavouriteStar(prefsArticle.checkArticleFavourite(article))
        }
    }

    private fun getArticleById() {
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