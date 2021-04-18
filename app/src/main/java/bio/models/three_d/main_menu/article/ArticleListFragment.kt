package bio.models.three_d.main_menu.article

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import bio.models.three_d.R
import bio.models.three_d.common.AdapterClick
import bio.models.three_d.common.AdapterListener
import bio.models.three_d.common.ArticleViewModel
import bio.models.three_d.databinding.FragmentArticleListBinding
import bio.models.three_d.main_menu.common.MainAdapter
import bio.models.three_d.main_menu.common.MarginItemDecoration
import bio.models.three_d.main_menu.common.article.ArticleData

class ArticleListFragment: Fragment(R.layout.fragment_article_list), AdapterListener {

    private lateinit var navArgs: ArticleListFragmentArgs
    private val model: ArticleViewModel by activityViewModels()
    private lateinit var binding : FragmentArticleListBinding
    private val listAdapter by lazy { MainAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navArgs = ArticleListFragmentArgs.fromBundle(requireArguments())
        binding = FragmentArticleListBinding.bind(view)
        initialize()
    }

    private fun initialize() {
        Log.d("TEST", "Initialize")
        setTitle()
        binding.articleRecycler.apply {
            addItemDecoration(
                MarginItemDecoration(
                    resources.getDimension(R.dimen.recycler_item_spacing).toInt()
                )
            )
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
        Log.d("TEST", "Initialized")
        listAdapter.submitList(ArticleData.createList())
    }

    private fun setTitle() {
        val themeList = resources.getStringArray(R.array.theme_titles)
        val themeId = navArgs.themeId
        if (themeId < themeList.size) {
            binding.homeTitle.text = themeList[themeId]
        }
    }

    override fun listen(click: AdapterClick?, position: Int) {
        findNavController().navigate(R.id.articleFragment)
    }

}