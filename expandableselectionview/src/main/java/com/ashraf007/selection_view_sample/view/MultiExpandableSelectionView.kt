package com.ashraf007.selection_view_sample.view

import android.content.Context
import android.util.AttributeSet

public class MultiExpandableSelectionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ExpandableSelectionView(context, attrs, defStyleAttr) {

    public var selectionListener: ((List<Int>) -> Unit)? = null

    override fun handleItemClick(index: Int) {
        if (isSelected(index)) {
            unSelectItem(index)
        } else {
            selectItem(index)
        }
        val selectedIndices = getSelectedIndices()
        if (selectedIndices.isEmpty()) {
            nothingSelectedListener?.invoke()
        } else {
            selectionListener?.invoke(selectedIndices)
        }
    }

    override fun selectIndex(index: Int) {
        if (!isSelected(index)) {
            selectItem(index)
            selectionListener?.invoke(getSelectedIndices())
        }
    }

    interface MultiSelectionCallback {
        fun onItemsSelected(index: List<Int>)
    }
}