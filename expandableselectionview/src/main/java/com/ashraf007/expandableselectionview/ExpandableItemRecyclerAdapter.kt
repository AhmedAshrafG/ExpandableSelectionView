package com.ashraf007.expandableselectionview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.areeb.doobocore.R

class ExpandableItemRecyclerAdapter(
    internal var adapter: ExpandableItemAdapter,
    private val showDividers: Boolean,
    private val itemClickCallback: (Int) -> Unit,
    private val isSelected: (Int) -> Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val contentView = adapter.getItemView(inflater, parent)
        val linearLayout = LinearLayout(parent.context)
        val dividerView = inflater.inflate(R.layout.divider, parent, false)

        if (!showDividers)
            dividerView.visibility = View.INVISIBLE

        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.addView(dividerView)
        linearLayout.addView(contentView)
        return ViewHolder(linearLayout)
    }

    override fun getItemCount(): Int = adapter.getItemsCount()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemView = (holder.itemView as ViewGroup).getChildAt(1)
        adapter.bindItemView(itemView, position)
        adapter.onItemSelectedStateChanged(itemView, isSelected(position))
        holder.itemView.setOnClickListener { itemClickCallback.invoke(position) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}