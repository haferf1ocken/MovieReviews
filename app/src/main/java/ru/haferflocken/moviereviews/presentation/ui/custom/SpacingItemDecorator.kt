package ru.haferflocken.moviereviews.presentation.ui.custom

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SpacingItemDecorator(
    private val horizontalSpacing: Int,
    private val verticalSpacing: Int,
    private val lastVerticalSpacing: Int = verticalSpacing,
    private val orientation: Int = LinearLayoutManager.VERTICAL
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val currentPosition = parent.getChildAdapterPosition(view)
        val lastPosition = parent.adapter?.itemCount?.minus(1) ?: 0

        if (orientation == LinearLayoutManager.VERTICAL) {
            outRect.right = horizontalSpacing
            outRect.left = horizontalSpacing

            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = verticalSpacing
            }

            if (parent.adapter != null && currentPosition
                == lastPosition) {
                outRect.bottom = lastVerticalSpacing
            } else {
                outRect.bottom = verticalSpacing
            }
        } else {
            outRect.top = verticalSpacing
            outRect.bottom = verticalSpacing

            if (currentPosition == 0) {
                outRect.left = horizontalSpacing * 2
            }

            if (parent.adapter != null && currentPosition
                == lastPosition) {
                outRect.right = horizontalSpacing * 2
            } else {
                outRect.right = horizontalSpacing
            }
        }
    }
}