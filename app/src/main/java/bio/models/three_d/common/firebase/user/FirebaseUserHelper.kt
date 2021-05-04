package bio.models.three_d.common.firebase.user

import android.content.Context
import android.util.Log
import bio.models.three_d.common.firebase.data.FirebaseDataHelper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient

object FirebaseUserHelper {

    private val TAG = this::class.java.simpleName

    fun getClient(context: Context): GoogleSignInClient? {
        val googleSignOptions = FirebaseDataHelper.getGoogleSignInOptions()
        var googleSignInClient: GoogleSignInClient? = null
        googleSignOptions?.let {
            googleSignInClient = GoogleSignIn.getClient(context, it)
        }
        return googleSignInClient
    }

    fun logOutClient(context: Context) {
        getClient(context)?.let {
            it.revokeAccess().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User access successfully revoked")
                    return@addOnCompleteListener
                }
                if (task.isCanceled) {
                    Log.d(TAG, "User access revoke canceled")
                    return@addOnCompleteListener
                }
            }
        }
    }

}