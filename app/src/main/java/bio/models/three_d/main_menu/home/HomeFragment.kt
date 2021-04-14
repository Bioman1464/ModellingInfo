
package bio.models.three_d.main_menu.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import bio.models.three_d.R
import bio.models.three_d.common.AdapterClick
import bio.models.three_d.common.AdapterListener
import bio.models.three_d.databinding.FragmentHomeBinding
import bio.models.three_d.main_menu.common.MarginItemDecoration
import bio.models.three_d.main_menu.home.theme.Theme
import bio.models.three_d.main_menu.common.MainAdapter
import bio.models.three_d.main_menu.home.theme.ThemeData

class HomeFragment : Fragment(R.layout.fragment_home), AdapterListener {

    private lateinit var binding : FragmentHomeBinding
    private val listAdapter by lazy { MainAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        initialize()
    }

    private fun initialize() {
        binding.themeRecycler.apply {
            addItemDecoration(MarginItemDecoration(
                resources
                    .getDimension(R.dimen.recycler_item_spacing)
                    .toInt()
            ))
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }

        listAdapter.submitList(ThemeData.createList())
    }

    override fun listen(click: AdapterClick?) {
        if (click is Theme) {
            Log.d("TEST", "Item is clicked: ${click.theme}")
        }
    }
}