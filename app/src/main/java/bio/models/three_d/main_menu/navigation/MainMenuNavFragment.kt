package bio.models.three_d.main_menu.navigation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import bio.models.three_d.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainMenuNavFragment : Fragment() {

    private lateinit var nestedNavController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_menu_nav, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nestedNavHostFragment = childFragmentManager
            .findFragmentById(R.id.menu_nav_host_fragment) as? NavHostFragment
        nestedNavHostFragment?.navController.let {
            nestedNavController = it!!
        }
        setupBottomNavMenu(view, nestedNavController)
    }

    private fun setupBottomNavMenu(view: View, navController: NavController) {
        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.run {
            setupWithNavController(navController)
            itemIconTintList = null
        }
    }

}