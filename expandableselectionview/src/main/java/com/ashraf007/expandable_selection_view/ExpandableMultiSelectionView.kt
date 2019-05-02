package com.ashraf007.expandable_selection_view

import android.content.Context
import android.util.AttributeSet
import com.ashraf007.expandable_selection_view.view.ExpandableSelectionView

class ExpandableMultiSelectionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ExpandableSelectionView(context, attrs, defStyleAttr) {

    var selectionListener: ((List<Int>) -> Unit)? = null
    val selectedIndices: List<Int>
        get() = getSelectedIndices()

    override fun handleItemClick(index: Int) {
        if (isSelected(index)) {
            unSelectItem(index)
        } else {
            selectItem(index)
        }
        val selectedIndices = getSelectedIndices()
        selectionListener?.invoke(selectedIndices)
    }

    override fun selectIndex(index: Int) {
        if (!isSelected(index)) {
            selectItem(index)
            selectionListener?.invoke(getSelectedIndices())
        }
    }
}