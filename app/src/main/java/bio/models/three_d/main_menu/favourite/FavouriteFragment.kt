package bio.models.three_d.main_menu.favourite

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import bio.models.three_d.R
import bio.models.three_d.common.AdapterClick
import bio.models.three_d.common.AdapterListener
import bio.models.three_d.common.RecyclerItem
import bio.models.three_d.common.SharedPrefs
import bio.models.three_d.databinding.FragmentFavouriteBinding
import bio.models.three_d.main_menu.common.MainAdapter
import bio.models.three_d.main_menu.common.MarginItemDecoration
import bio.models.three_d.main_menu.common.article.Article
import bio.models.three_d.main_menu.common.article.ArticleData

class FavouriteFragment : Fragment(R.layout.fragment_favourite), AdapterListener {

    private lateinit var binding : FragmentFavouriteBinding
    private val listAdapter by lazy { MainAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavouriteBinding.bind(view)
        initialize()
    }

    private fun initialize() {
        binding.articleRecycler.apply {
            /*addItemDecoration(
                MarginItemDecoration(
                    resources
                        .getDimension(R.dimen.recycler_item_spacing).toInt()
                ))*/
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
        listAdapter.submitList(getFavouriteArticles())
    }

    private fun getFavouriteArticles(): MutableList<RecyclerItem> {
        val sharedPrefs = SharedPrefs.getInstance(requireContext())
        val favouriteArticleIds = sharedPrefs.retrieveFavouriteArticleList()
        val favouriteArticles = mutableListOf<RecyclerItem>()
        for (item in favouriteArticleIds) {
            favouriteArticles.add(ArticleData.getById(requireContext(), item.id))
        }
        return favouriteArticles
    }

    override fun listen(click: AdapterClick?, position: Int) {
        if (click is Article) {
            listAdapter.currentList[position].id?.toInt()?.let {
                val action = FavouriteFragmentDirections
                    .actionFavouriteFragmentToArticleFragment(articleId = it)
                findNavController().navigate(action)
            }
        }
    }

    fun likeArticle(click: Article, position: Int) {
        if (listAdapter.currentList[position] is Article) {
            Log.d("TEST", "Item is clicked: " +
                    "${(listAdapter.currentList[position] as Article).isFavourite}")
            if ((listAdapter.currentList[position] as Article).isFavourite != click.isFavourite) {
                (listAdapter.currentList[position] as Article).isFavourite = !(listAdapter.currentList[position] as Article).isFavourite
                listAdapter.notifyItemChanged(position)
                Log.d("FAVORITE", "$position")
                return
            }
        }
    }
}