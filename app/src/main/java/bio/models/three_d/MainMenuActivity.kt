package bio.models.three_d

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import bio.models.three_d.common.ArticleSharedPrefs
import bio.models.three_d.common.UserAccount
import bio.models.three_d.common.data.Article
import bio.models.three_d.common.data.ArticleHelper
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainMenuActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var valueListener: ValueEventListener
    private val TAG = "DATABASE"

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
                updateAccountData(user.uid, user.photoUrl, user.displayName)
                parseActualDBData(user.uid)
            }
        }
    }

    private fun parseActualDBData(uid: String) {
        val dbReference = FirebaseDatabase.getInstance(getString(R.string.database_reference))
            .reference
        dbReference.child("users").child(uid).child("favourites").get()
            .addOnCompleteListener(onCompleteListener())
    }

    private fun parseFavouriteList(articleIdRawList: String) {
        val result: List<String> = articleIdRawList.split(",").map { it.trim() }
        val articleList: MutableList<Article> = mutableListOf()
        result.forEach { id ->
            articleList.add(Article(id.toInt(), ArticleHelper.themeIdById(id.toInt())))
        }
        saveFavouriteList(articleList)
    }

    private fun saveFavouriteList(articleList: List<Article>) {
        val sharedPreferences = ArticleSharedPrefs.getInstance(this)
        sharedPreferences.reloadFavouriteArticleList(articleList)
    }

    private fun onCompleteListener (): OnCompleteListener<DataSnapshot?> {
        return object : OnCompleteListener<DataSnapshot?> {
            override fun onComplete(snapshot: Task<DataSnapshot?>) {
                if (snapshot.isSuccessful) {
                    if (snapshot.result?.value.toString().isEmpty()
                        || snapshot.result?.value.toString() == "null"
                    ) {
                        return
                    }
                    parseFavouriteList(snapshot.result?.value.toString())
                    return
                }
                Log.e(TAG, "Error getting data", snapshot.exception);
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
}