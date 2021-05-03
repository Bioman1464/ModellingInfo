package bio.models.three_d.common.firebase.data

import android.content.Context
import android.content.Intent
import android.util.Log
import bio.models.three_d.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*

object FirebaseDataHelper {

    private val TAG = this::class.java.simpleName

    fun getGoogleSignInOptions(context: Context): GoogleSignInOptions? {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).run {
            requestIdToken(context.getString(R.string.sign_in_key_id))
            requestEmail()
            requestId()
            build()
        }
    }

    fun getSignInIntent(context: Context): Intent? {
        val googleSignInOptions = getGoogleSignInOptions(context)
        var signInIntent: Intent? = null
        googleSignInOptions?.let {
            val mGoogleSignInClient = GoogleSignIn.getClient(context, it)
            signInIntent = mGoogleSignInClient.signInIntent
        }
        return signInIntent
    }

    private fun getDatabaseReference(context: Context): DatabaseReference {
        return FirebaseDatabase
            .getInstance(context.getString(R.string.database_reference))
            .reference
    }

    fun getUserFavouriteReference (context: Context, uid: String): DatabaseReference {
        val dbReference = getDatabaseReference(context)
        return dbReference.child("users").child(uid).child("favourites")
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