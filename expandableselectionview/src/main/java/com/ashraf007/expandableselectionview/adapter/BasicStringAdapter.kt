package com.ashraf007.expandableselectionview.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import com.ashraf007.expandableselectionview.R
import com.ashraf007.expandableselectionview.extension.inflate
import com.ashraf007.expandableselectionview.view.ExpandableSelectionView

open class BasicStringAdapter(
    private val items: List<String>,
    var hint: String? = null
) : ExpandableItemAdapter {

    @DrawableRes
    var selectedStateResId: Int? = null
    @DrawableRes
    var collapsedStateResId: Int? = null
    @DrawableRes
    var expandedStateResId: Int? = null

    override fun inflateHeaderView(parent: ViewGroup): View {
        val view = parent.inflate(R.layout.basic_expandable_header_layout)
        val headerTV = view.findViewById<TextView>(R.id.headerTV)
        headerTV.hint = hint
        return view
    }

    override fun inflateItemView(parent: ViewGroup) =
        parent.inflate(R.layout.basic_expandable_item_layout)

    override fun bindItemView(itemView: View, position: Int, selected: Boolean) {
        val itemTV = itemView.findViewById<TextView>(R.id.itemNameTV)
        itemTV.text = items[position]
        itemView.findViewById<ImageView>(R.id.selectionIV)
            .setImageResource(selectedStateResId ?: R.drawable.ic_selected)
        itemView.findViewById<View>(R.id.selectionIV).isVisible = selected
    }

    override fun bindHeaderView(headerView: View, selectedIndices: List<Int>) {
        val headerTV = headerView.findViewById<TextView>(R.id.headerTV)
        if (selectedIndices.isEmpty()) {
            headerTV.text = null
        } else {
            headerTV.text = selectedIndices.joinToString { items[it] }
        }
    }

    override fun getItemsCount() = items.size

    override fun onViewStateChanged(headerView: View, state: ExpandableSelectionView.State) {
        val imageView = headerView.findViewById<ImageView>(R.id.listIndicatorIV)
        imageView.setImageResource(
            when (state) {
                ExpandableSelectionView.State.Expanded ->
                    expandedStateResId ?: R.drawable.ic_expanded_arrow
                ExpandableSelectionView.State.Collapsed ->
                    collapsedStateResId ?: R.drawable.ic_collapsed_arrow
            }
        )
    }
}