package com.ashraf007.expandable_selection_view.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import com.ashraf007.expandable_selection_view.R
import com.ashraf007.expandable_selection_view.extension.inflate
import com.ashraf007.expandable_selection_view.view.ExpandableSelectionView

open class BasicStringAdapter(
    private val items: List<String>,
    private val hint: String? = null
) : ExpandableItemAdapter {

    @DrawableRes
    var selectedStateResId: Int? = null
    @DrawableRes
    var collapsedStateResId: Int? = null
    @DrawableRes
    var expandedStateResId: Int? = null

    override fun inflateHeaderView(parent: ViewGroup) =
        parent.inflate(R.layout.expandable_string_header_layout)

    override fun inflateItemView(parent: ViewGroup) =
        parent.inflate(R.layout.expandable_string_item_layout)

    override fun bindItemView(itemView: View, position: Int, selected: Boolean) {
        val itemTV = itemView.findViewById<TextView>(R.id.text_field)
        itemTV.text = items[position]
        itemView.findViewById<ImageView>(R.id.selected_mark)
            .setImageResource(selectedStateResId ?: R.drawable.ic_selected)
        itemView.findViewById<View>(R.id.selected_mark).isVisible = selected
    }

    override fun bindHeaderView(headerView: View, selectedIndices: List<Int>) {
        val headerTV = headerView.findViewById<TextView>(R.id.header_tv)
        if (selectedIndices.isEmpty()) {
            headerTV.hint = hint
            headerTV.text = null
        } else {
            headerTV.text = selectedIndices.joinToString { items[it] }
        }
    }

    override fun getItemsCount() = items.size

    override fun onViewStateChanged(headerView: View, state: ExpandableSelectionView.State) {
        val imageView = headerView.findViewById<ImageView>(R.id.list_indicator)
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