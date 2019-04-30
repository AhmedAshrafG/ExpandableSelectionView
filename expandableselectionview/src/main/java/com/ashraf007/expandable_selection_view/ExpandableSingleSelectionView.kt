package com.ashraf007.expandable_selection_view

import android.content.Context
import android.util.AttributeSet
import com.ashraf007.expandable_selection_view.view.ExpandableSelectionView

class ExpandableSingleSelectionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ExpandableSelectionView(context, attrs, defStyleAttr) {

    private var selectionListener: ((Int?) -> Unit)? = null

    override fun handleItemClick(index: Int) {
        val selectedItems = getSelectedIndices()
        if (isSelected(index)) {
            unSelectItem(index)
            selectionListener?.invoke(null)
        } else {
            if (selectedItems.isNotEmpty())
                unSelectItem(selectedItems.first())
            selectItem(index)
            selectionListener?.invoke(index)
        }
        setState(State.Collapsed)
    }

    override fun selectIndex(index: Int) {
        if (!isSelected(index)) {
            val selectedItems = getSelectedIndices()
            if (selectedItems.isNotEmpty())
                unSelectItem(selectedItems.first())
            selectItem(index)
            selectionListener?.invoke(index)
        }
    }

    fun getSelectedIndex() = getSelectedIndices().firstOrNull()
}