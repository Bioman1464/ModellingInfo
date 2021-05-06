package bio.models.three_d

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bio.models.three_d.common.UserAccount
import bio.models.three_d.common.firebase.data.FirebaseDataHelper
import bio.models.three_d.main_menu.common.article.ArticleData
import bio.models.three_d.main_menu.home.theme.ThemeData
import com.google.firebase.auth.FirebaseAuth

class MainMenuActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
//        val host: NavHostFragment = supportFragmentManager
//            .findFragmentById(R.id.main_nav_graph) as NavHostFragment? ?: return
//        val navController = host.navController
        getFirebaseUser()
        initAppData()
    }

    private fun initAppData() {
        ThemeData.recreateList(this)
    }

    private fun getFirebaseUser() {
        auth = FirebaseAuth.getInstance()
        auth.let {
            val user = auth.currentUser
            user?.let {
                updateAccountData(user.uid, user.photoUrl, user.displayName)
                updateLocalStorage(user.uid)
            }
        }
    }

    private fun updateAccountData(userUid: String?, userPhotoUrl: Uri?, userName: String?) {
        UserAccount.run {
            if (userUid != null) {
                uid = userUid
            }
            name = userName
            photoUrl = userPhotoUrl
            logData()
        }
    }

    private fun updateLocalStorage(uid: String) {
        FirebaseDataHelper.getUserFavouriteArticles(uid, this)
    }
}