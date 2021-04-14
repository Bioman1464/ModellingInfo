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
        Log.d("TEST", "Initialize")
        binding.articleRecycler.apply {
            addItemDecoration(
                MarginItemDecoration(
                    resources
                        .getDimension(R.dimen.recycler_item_spacing).toInt()
                ))
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
        Log.d("TEST", "Initialized")
        listAdapter.submitList(ArticleData.createList())
    }

    override fun listen(click: AdapterClick?, position: Int) {
        if (click is Article) {
            Log.d("TEST", "Item is clicked: ${click.title}")
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

//            findNavController().navigate(R.id.articleFragment)
        }
    }
}