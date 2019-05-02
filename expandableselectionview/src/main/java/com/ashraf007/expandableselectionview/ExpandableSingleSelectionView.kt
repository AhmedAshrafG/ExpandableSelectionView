package com.ashraf007.expandableselectionview

import android.content.Context
import android.util.AttributeSet
import com.ashraf007.expandableselectionview.view.ExpandableSelectionView

class ExpandableSingleSelectionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ExpandableSelectionView(context, attrs, defStyleAttr) {

    var selectionListener: ((Int?) -> Unit)? = null
    val selectedIndex: Int?
        get() = getSelectedIndices().firstOrNull()

    override fun handleItemClick(index: Int) {
        val selectedItems = getSelectedIndices()
        if (isSelected(index)) {
            unSelectItem(index)
            notifySelectionListener(null)
        } else {
            if (selectedItems.isNotEmpty())
                unSelectItem(selectedItems.first())
            selectItem(index)
            notifySelectionListener(index)
        }
        setState(State.Collapsed)
    }

    fun selectIndex(index: Int) {
        if (!isSelected(index)) {
            val selectedItems = getSelectedIndices()
            if (selectedItems.isNotEmpty())
                unSelectItem(selectedItems.first())
            selectItem(index)
            notifySelectionListener(index)
        }
    }

    override fun clearSelection() {
        super.clearSelection()
        notifySelectionListener(null)
    }

    private fun notifySelectionListener(index: Int?) {
        selectionListener?.invoke(index)
    }
}