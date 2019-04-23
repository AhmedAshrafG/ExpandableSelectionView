package com.ashraf007.expandableselectionview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

interface ExpandableItemAdapter {
    fun getHeaderView(inflater: LayoutInflater, parent: ViewGroup): View

    fun getItemView(inflater: LayoutInflater, parent: ViewGroup): View

    fun bindItemView(itemView: View, position: Int)

    fun bindSelectedItem(headerView: View, selectedIndices: List<Int>)

    fun onExpandableStateChanged(headerView: View, state: ExpandableSelectionView.State)

    fun onItemSelectedStateChanged(itemView: View, selected: Boolean)

    fun getItemsCount(): Int
}