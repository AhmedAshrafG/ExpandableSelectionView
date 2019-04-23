package com.ashraf007.expandableselectionview

import android.content.Context
import android.util.AttributeSet

public class SingleExpandableSelectionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ExpandableSelectionView(context, attrs, defStyleAttr) {

    public var selectionListener: ((Int) -> Unit)? = null

    override fun handleItemClick(index: Int) {
        val selectedItems = getSelectedIndices()
        if (isSelected(index)) {
            unSelectItem(index)
            nothingSelectedListener?.invoke()
        } else {
            if (selectedItems.isNotEmpty())
                unSelectItem(selectedItems.first())
            selectItem(index)
            selectionListener?.invoke(index)
        }
        setState(State.Collapsed)
    }

    override fun selectIndex(index: Int) {
        val selectedItems = getSelectedIndices()
        if (!isSelected(index)) {
            if (selectedItems.isNotEmpty())
                unSelectItem(selectedItems.first())
            selectItem(index)
            selectionListener?.invoke(index)
        }
    }

    public fun getSelectedIndex() = getSelectedIndices().firstOrNull()
}

interface SingleSelectionCallback {
    fun onItemSelected(index: Int)
}