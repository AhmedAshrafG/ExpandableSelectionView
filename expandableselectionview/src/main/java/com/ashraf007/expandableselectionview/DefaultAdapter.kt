package com.ashraf007.expandableselectionview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class DefaultAdapter : ExpandableItemAdapter {
    override fun getHeaderView(inflater: LayoutInflater, parent: ViewGroup) = View(parent.context)

    override fun getItemView(inflater: LayoutInflater, parent: ViewGroup) = View(parent.context)

    override fun bindItemView(itemView: View, position: Int) {}

    override fun bindSelectedItem(headerView: View, selectedIndices: List<Int>) {}

    override fun onExpandableStateChanged(headerView: View, state: ExpandableSelectionView.State) {}

    override fun onItemSelectedStateChanged(itemView: View, selected: Boolean) {}

    override fun getItemsCount() = 0
}
