package com.ashraf007.expandableselectionview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.areeb.doobocore.R

class StringAdapter(private val items: List<String>, private val hint: String?) : ExpandableItemAdapter {

    @SuppressLint("SetTextI18n")
    override fun bindItemView(itemView: View, position: Int) {
        val itemTV = itemView.findViewById<TextView>(R.id.text_field)
        itemTV.text = items[position]
        itemView.findViewById<ImageView>(R.id.selected_mark).setImageResource(R.drawable.marked_shape)
    }

    override fun getHeaderView(inflater: LayoutInflater, parent: ViewGroup): View =
        inflater.inflate(R.layout.expandable_header, parent, false)

    override fun getItemView(inflater: LayoutInflater, parent: ViewGroup): View =
        inflater.inflate(R.layout.selection_item, parent, false)

    override fun bindSelectedItem(headerView: View, selectedIndices: List<Int>) {
        val headerTV = headerView.findViewById<TextView>(R.id.header_tv)
        if (selectedIndices.isEmpty()) {
            headerTV.hint = hint
            headerTV.text = null
        } else {
            headerTV.text = selectedIndices.joinToString(", ") { items[it] }
        }
    }

    override fun getItemsCount(): Int = items.size

    override fun onExpandableStateChanged(headerView: View, state: ExpandableSelectionView.State) {
        val imageView = headerView.findViewById<ImageView>(R.id.list_indicator)
        when (state) {
            ExpandableSelectionView.State.Expanded -> imageView.setImageResource(R.drawable.expanded_arrow)
            ExpandableSelectionView.State.Collapsed -> imageView.setImageResource(R.drawable.collapsed_arrow)
        }
    }

    override fun onItemSelectedStateChanged(itemView: View, selected: Boolean) {
        itemView.findViewById<View>(R.id.selected_mark).visibility = when (selected) {
            true -> View.VISIBLE
            false -> View.INVISIBLE
        }
    }
}
