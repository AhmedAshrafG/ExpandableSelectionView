package com.ashraf007.expandable_selection_view.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.ashraf007.expandable_selection_view.extension.collapse
import com.ashraf007.expandable_selection_view.extension.expand

class ExpandableRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var animating = false
    var maxHeight: Int = Int.MAX_VALUE

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val newHeightSpec = when {
            animating -> heightMeasureSpec
            else -> MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST)
        }
        super.onMeasure(widthMeasureSpec, newHeightSpec)
    }

    fun expand(animationDurationScale: Int) {
        this.animating = true
        this.expand(maxHeight, animationDurationScale) {
            this.animating = false
        }
    }

    fun collapse(animationDurationScale: Int) {
        this.animating = true
        this.collapse(animationDurationScale) {
            this.animating = false
        }
    }
}
