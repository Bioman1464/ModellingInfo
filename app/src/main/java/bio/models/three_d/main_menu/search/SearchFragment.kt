package bio.models.three_d.main_menu.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import bio.models.three_d.R
import bio.models.three_d.common.AdapterClick
import bio.models.three_d.common.AdapterListener
import bio.models.three_d.databinding.FragmentHomeBinding
import bio.models.three_d.databinding.FragmentSearchBinding
import bio.models.three_d.main_menu.common.MainAdapter
import bio.models.three_d.main_menu.common.MarginItemDecoration
import bio.models.three_d.main_menu.home.theme.Theme
import bio.models.three_d.main_menu.home.theme.ThemeData

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
            addItemDecoration(
                MarginItemDecoration(
                resources
                    .getDimension(R.dimen.recycler_item_spacing)
                    .toInt()
            ))
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }

        listAdapter.submitList(ThemeData.createList())
    }

    override fun listen(click: AdapterClick?, position: Int) {
        if (click is Theme) {
            Log.d("TEST", "Item is clicked: ${click.theme}")
        }
    }

}