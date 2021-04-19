package bio.models.three_d.main_menu.article

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import bio.models.three_d.R
import bio.models.three_d.databinding.FragmentArticleBinding

class ArticleFragment : Fragment(R.layout.fragment_article) {

    private lateinit var navArgs: ArticleFragmentArgs
    private lateinit var binding: FragmentArticleBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navArgs = ArticleFragmentArgs.fromBundle(requireArguments())
        binding = FragmentArticleBinding.bind(view)
        setTitle()
        showArticleFromHtml("myfile.html")
    }

    private fun setTitle() {
        val articleId = navArgs.articleId
        val themeList = resources.getStringArray(R.array.tech_article_titles)
        articleId.let {
            binding.articleTitle.text = themeList[it]
            getArticleById(it)
        }
    }

    private fun changeFavourite() {
    }

    private fun getArticleById(id: Int) {

    }

    private fun showArticleFromHtml(fileName: String) {
        binding.articleWebView.run {
            settings.javaScriptEnabled = true
            setBackgroundColor(Color.TRANSPARENT)
            loadUrl("file:///android_asset/$fileName")
        }
    }
}