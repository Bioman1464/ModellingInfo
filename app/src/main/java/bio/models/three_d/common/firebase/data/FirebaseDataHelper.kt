package bio.models.three_d.common.firebase.data

import android.content.Context
import android.content.Intent
import android.util.Log
import bio.models.three_d.FirebaseSecrets
import bio.models.three_d.common.shared_preferences.ArticleSharedPrefs
import bio.models.three_d.common.data.Article
import bio.models.three_d.common.data.ArticleHelper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*

object FirebaseDataHelper {

    private val TAG = this::class.java.simpleName

    fun getGoogleSignInOptions(): GoogleSignInOptions? {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).run {
            requestIdToken(FirebaseSecrets.signInKey)
            requestEmail()
            requestId()
            build()
        }
    }

    fun getSignInIntent(context: Context): Intent? {
        val googleSignInOptions = getGoogleSignInOptions()
        var signInIntent: Intent? = null
        googleSignInOptions?.let {
            val mGoogleSignInClient = GoogleSignIn.getClient(context, it)
            signInIntent = mGoogleSignInClient.signInIntent
        }
        return signInIntent
    }

    private fun getDatabaseReference(context: Context): DatabaseReference {
        return FirebaseDatabase
            .getInstance(FirebaseSecrets.databaseReference)
            .reference
    }

    fun getUserFavouriteReference (context: Context, uid: String): DatabaseReference {
        val dbReference = getDatabaseReference(context)
        return dbReference.child("users").child(uid).child("favourites")
    }

    fun getUserFavouriteArticles(uid: String, context: Context) {
        getUserFavouriteReference(context, uid)
            .get()
            .addOnCompleteListener(onCompleteListener {
                val articles = parseFavouriteList(it)
                saveFavouriteList(articles, context)
            })
    }

    private fun parseFavouriteList(articleIdRawList: String): List<Article> {
        val articles: List<Article> = ArticleHelper.parseRawArticleIds(articleIdRawList)
        return articles
    }

    private fun saveFavouriteList(articleList: List<Article>, context: Context) {
        Log.d(TAG, "Save articleList: ${articleList.toString()}")
        val sharedPreferences = ArticleSharedPrefs.getInstance(context)
        sharedPreferences.reloadFavouriteArticleList(articleList)
    }

    fun saveUsersFavourite (newValue: String, databaseReference: DatabaseReference,
                            valueEventListener: ValueEventListener = defaultValueListener()) {
        databaseReference.addValueEventListener(valueEventListener)
        databaseReference.setValue(newValue)
    }

    private fun defaultValueListener() : ValueEventListener {
        return object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d(TAG, "Success -> Data: ${snapshot.value}")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "Canceled")
            }
        }
    }

    fun onCompleteListener (dataAction: (data: String) -> Unit): OnCompleteListener<DataSnapshot?> {
        return object : OnCompleteListener<DataSnapshot?> {
            override fun onComplete(snapshot: Task<DataSnapshot?>) {
                if (snapshot.isSuccessful) {
                    /*if (snapshot.result?.value.toString().isEmpty()
                        || snapshot.result?.value.toString() == "null"
                    ) {
                        return
                    }*/
                    dataAction(snapshot.result!!.value.toString())
                    return
                }
                Log.e(TAG, "Error getting data", snapshot.exception);
            }
        }
    }

}