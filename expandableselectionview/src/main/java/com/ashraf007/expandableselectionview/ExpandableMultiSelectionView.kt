package com.ashraf007.expandableselectionview

import android.content.Context
import android.util.AttributeSet
import com.ashraf007.expandableselectionview.view.ExpandableSelectionView

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
        notifySelectionListener()
    }

    override fun clearSelection() {
        super.clearSelection()
        selectionListener?.invoke(emptyList())
    }

    fun selectIndex(index: Int, notifyListener: Boolean = true) {
        if (!isSelected(index)) {
            selectItem(index)
            if (notifyListener) {
                notifySelectionListener()
            }
        }
    }

    fun unSelectIndex(index: Int, notifyListener: Boolean = true) {
        if (isSelected(index)) {
            unSelectItem(index)
            if (notifyListener) {
                notifySelectionListener()
            }
        }
    }

    fun selectIndices(indices: List<Int>, notifyListener: Boolean = true) {
        indices.filterNot(::isSelected)
            .forEach(::selectItem)
        if (notifyListener) {
            notifySelectionListener()
        }
    }

    fun unSelectIndices(indices: List<Int>, notifyListener: Boolean = true) {
        indices.filter(::isSelected)
            .forEach(::unSelectItem)
        if (notifyListener) {
            notifySelectionListener()
        }
    }

    private fun notifySelectionListener() {
        selectionListener?.invoke(selectedIndices)
    }
}
