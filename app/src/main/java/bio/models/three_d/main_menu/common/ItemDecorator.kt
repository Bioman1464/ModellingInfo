package bio.models.three_d.main_menu.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(
    private val spaceHeight: Int,
    private val horizontal: Boolean = false) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = spaceHeight
            }
            if (horizontal) {
                left =  spaceHeight
                right = spaceHeight
            }
            bottom = spaceHeight
        }
    }
}