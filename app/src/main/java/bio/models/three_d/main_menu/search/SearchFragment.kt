package bio.models.three_d.main_menu.search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
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

    private lateinit var binding: FragmentSearchBinding
    private val listAdapter by lazy { MainAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        initialize()
    }

    private fun initialize() {
        binding.articleSearch.requestFocus()
        binding.infoLayout.searchInfoTitle.text =
            requireContext().resources.getText(R.string.enter_at_least_two_symbols)
        binding.infoLayout.root.visibility = View.VISIBLE
        binding.searchRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
        binding.articleSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listAdapter.submitList(null)
                binding.searchRecycler.visibility = View.GONE
                binding.infoLayout.root.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length < 2) {
                    binding.searchRecycler.visibility = View.GONE
                    binding.infoLayout.searchInfoTitle.text =
                        requireContext().resources.getText(R.string.enter_at_least_two_symbols)
                    binding.infoLayout.root.visibility = View.VISIBLE
                    return
                }
                val articleList = ArticleData.getList(requireContext())
                val filteredArticleList = SearchArticleData
                    .filterArticleItems(s.toString(), articleList)
                Log.d("Error", "${articleList.size}")
                try {
                    if (filteredArticleList.isNullOrEmpty()) {
                        binding.searchRecycler.visibility = View.GONE
                        binding.infoLayout.searchInfoTitle.text =
                            requireContext().resources.getText(R.string.nothing_was_found)
                        binding.infoLayout.root.visibility = View.VISIBLE
                        return
                    }
                } catch (e: Throwable) {
                    Log.d("Error", "${e.message}")
                }
                listAdapter.submitList(filteredArticleList)
                binding.searchRecycler.visibility = View.VISIBLE
                binding.infoLayout.root.visibility = View.GONE
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

    override fun onStart() {
        super.onStart()
        Log.d(this::class.java.simpleName, "OnStart")
        binding.articleSearch.requestFocus()
        showKeyboard(requireContext())
    }

    override fun onStop() {
        super.onStop()
        Log.d(this::class.java.simpleName, "OnStop")
        binding.articleSearch.requestFocus()
        hideKeyboard(requireContext())
    }

    private fun hideKeyboard(context: Context) {
        try {
            (context as Activity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            if (context.currentFocus != null && context.currentFocus!!
                    .windowToken != null
            ) {
                (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    context.currentFocus!!.windowToken, 0
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showKeyboard(context: Context) {
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
            InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }
}