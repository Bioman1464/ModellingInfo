package bio.models.three_d.main_menu.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import bio.models.three_d.R
import bio.models.three_d.common.AdapterClick
import bio.models.three_d.common.AdapterListener
import bio.models.three_d.databinding.FragmentSearchBinding
import bio.models.three_d.main_menu.common.MainAdapter
import bio.models.three_d.main_menu.common.article.ArticleData
import bio.models.three_d.main_menu.search.search_recycler.SearchArticle
import bio.models.three_d.main_menu.search.search_recycler.SearchArticleData

class SearchFragment : Fragment(R.layout.fragment_search), AdapterListener {

    private lateinit var binding : FragmentSearchBinding
    private val listAdapter by lazy { MainAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        initialize()
    }

    private fun initialize() {
        binding.searchRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
        binding.articleSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listAdapter.submitList(null)
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isBlank()) {
                    return
                }
                listAdapter.submitList(
                    SearchArticleData.filterArticleItems(
                        s.toString(), ArticleData.getList(requireContext())
                    )
                )
            }
        })
    }

    override fun listen(click: AdapterClick?, position: Int) {
        if (click is SearchArticle) {
            Log.d("TEST", "Item is clicked: ${click.title}")
            val action = listAdapter.currentList[position].id?.toInt()?.let {
                SearchFragmentDirections
                    .actionSearchFragmentToArticleFragment(articleId = it)
            }
            action.let {
                findNavController().navigate(it!!)
            }
        }
    }

}