package com.ashraf007.expandableselectionview

import android.content.Context
import android.util.AttributeSet
import com.ashraf007.expandableselectionview.view.ExpandableSelectionView

class ExpandableSingleSelectionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ExpandableSelectionView(context, attrs, defStyleAttr) {

    var autoCollapseOnSelection: Boolean = true

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
        if (autoCollapseOnSelection)
            setState(State.Collapsed)
    }

    fun selectIndex(index: Int, notifyListener: Boolean = true) {
        if (!isSelected(index)) {
            val selectedItems = getSelectedIndices()
            if (selectedItems.isNotEmpty()) {
                unSelectItem(selectedItems.first())
            }
            selectItem(index)
            if (notifyListener) {
                notifySelectionListener(index)
            }
        }
    }

    override fun clearSelection() {
        super.clearSelection()
        notifySelectionListener(null)
    }

    private var onSelectionChangeListener: Interfaces.SelectedItemChanged? = null

    fun setOnSelectionChange(listener: Interfaces.SelectedItemChanged) {
        onSelectionChangeListener = listener
    }

    private fun notifySelectionListener(index: Int?) {
        onSelectionChangeListener?.onSelectionChange(index)
    }
}