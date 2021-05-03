package bio.models.three_d

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import bio.models.three_d.common.ArticleSharedPrefs
import bio.models.three_d.common.UserAccount
import bio.models.three_d.common.data.Article
import bio.models.three_d.common.data.ArticleHelper
import bio.models.three_d.common.firebase.data.FirebaseDataHelper
import com.google.firebase.auth.FirebaseAuth

//TODO:: move get favourite article to favourite page
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
    }

    private fun getFirebaseUser() {
        auth = FirebaseAuth.getInstance()
        auth.let {
            val user = auth.currentUser
            user?.let {
                updateAccountData(user.uid, user.photoUrl, user.displayName)
                parseActualDBData(user.uid)
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

    private fun parseActualDBData(uid: String) {
        FirebaseDataHelper
            .getUserFavouriteReference(this, uid)
            .get()
            .addOnCompleteListener(FirebaseDataHelper.onCompleteListener(::parseFavouriteList))
    }

    private fun parseFavouriteList(articleIdRawList: String) {
        val articles: List<Article> = ArticleHelper.parseRawArticleIds(articleIdRawList)
        saveFavouriteList(articles)
    }

    private fun saveFavouriteList(articleList: List<Article>) {
        Log.d(TAG, "Save articleList: ${articleList.toString()}")
        val sharedPreferences = ArticleSharedPrefs.getInstance(this)
        sharedPreferences.reloadFavouriteArticleList(articleList)
    }
}