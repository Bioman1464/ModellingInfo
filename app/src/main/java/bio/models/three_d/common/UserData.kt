package bio.models.three_d.common

import android.net.Uri
import android.util.Log

object UserAccount {
    var uid: String = ""
    var name: String? = null
    var photoUrl: Uri? = null

    fun clearData() {
        uid = ""
        name = null
        photoUrl = null
    }

    fun logData() {
        Log.d("UserData", "$uid $name $photoUrl")
    }
}