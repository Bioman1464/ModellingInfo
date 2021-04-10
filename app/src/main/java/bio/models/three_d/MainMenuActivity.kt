package bio.models.three_d

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_nav_graph) as NavHostFragment? ?: return

        val navController = host.navController

    }

}