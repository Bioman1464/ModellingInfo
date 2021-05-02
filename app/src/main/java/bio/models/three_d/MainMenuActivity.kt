package bio.models.three_d

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bio.models.three_d.common.UserAccount
import com.google.firebase.auth.FirebaseAuth


class MainMenuActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

//        val host: NavHostFragment = supportFragmentManager
//            .findFragmentById(R.id.main_nav_graph) as NavHostFragment? ?: return
//        val navController = host.navController
        auth = FirebaseAuth.getInstance()
        getFirebaseUser()
    }

    private fun getFirebaseUser() {
        auth.let {
            val user = auth.currentUser
            user?.let {
                updateAccountUiInfo(user.uid, user.photoUrl, user.displayName)
            }
        }
    }

    private fun updateAccountUiInfo(userUid: String?, userPhotoUrl: Uri?, userName: String?) {
        UserAccount.run {
            if (userUid != null) {
                uid = userUid
            }
            name = userName
            photoUrl = userPhotoUrl
            logData()
        }
    }
}