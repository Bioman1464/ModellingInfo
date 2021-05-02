package bio.models.three_d.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import java.net.URL

class ImageDownloadTask(userImageView: ImageView) : AsyncTask<String, Void, Bitmap>() {

    private val userImageView: ImageView = userImageView

    override fun doInBackground(vararg params: String?): Bitmap {
        val imageUrl = URL(params[0])
        return BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream())
    }

    override fun onPostExecute(result: Bitmap?) {
        super.onPostExecute(result)
        userImageView.setImageBitmap(result)
    }

}