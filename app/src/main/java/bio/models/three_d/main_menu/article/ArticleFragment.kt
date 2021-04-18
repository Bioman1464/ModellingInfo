package bio.models.three_d.main_menu.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }

    private fun setTitle() {
        val articleId = navArgs.articleId
        articleId.let {
            binding.articleTitle.text = it.toString()
            getArticleById(it)
        }
    }

    private fun getArticleById(id: Int) {

    }
}