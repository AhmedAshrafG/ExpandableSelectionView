package com.ashraf007.expandable_selection_view.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.withStyledAttributes
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashraf007.expandable_selection_view.R
import com.ashraf007.expandable_selection_view.adapter.ExpandableItemAdapter
import com.ashraf007.expandable_selection_view.adapter.ExpandableItemRecyclerAdapter
import com.ashraf007.expandable_selection_view.extension.inflate

abstract class ExpandableSelectionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val contentLayout: LinearLayout
    private val itemsRecyclerView: ExpandableRecyclerView
    private val errorLabel: TextView
    private var headerView: View

    private var currentState: State = State.Collapsed
    private var showDividers: Boolean = true
    private var dividerColor: Int = Color.parseColor("#668b999f")
    private var maxHeight: Int = Int.MAX_VALUE
    private var animationDuration: Long = DEFAULT_ANIMATION_DURATION_SCALE
    private var selectedIndices: MutableList<Int> = mutableListOf()

    private var expandableSelectionAdapter: ExpandableItemAdapter? = null
    private var recyclerAdapter: ExpandableItemRecyclerAdapter? = null

    init {
        this.orientation = VERTICAL

        contentLayout = LinearLayout(context)
        contentLayout.orientation = VERTICAL

        itemsRecyclerView = inflate(R.layout.expandable_recycler_view_layout) as ExpandableRecyclerView
        errorLabel = inflate(R.layout.error_field_layout) as TextView
        headerView = View(context)

        this.addView(contentLayout)
        this.addView(errorLabel)

        attrs?.let { drawWithAttrs(it) }
    }

    fun setAdapter(adapter: ExpandableItemAdapter) {
        this.expandableSelectionAdapter = adapter
        val recyclerAdapter = ExpandableItemRecyclerAdapter(
            adapter,
            ::handleItemClick,
            ::isSelected
        ).also {
            it.showDividers = showDividers
            it.dividerColor = dividerColor
        }
        setRecyclerAdapter(recyclerAdapter)
        addContentViews(adapter)
        initState()
    }

    fun setError(errorStr: String?) {
        errorLabel.isGone = (errorStr == null)
        errorLabel.text = errorStr
    }

    fun setState(state: State) {
        if (currentState == state)
            return
        toggleAndSetState()
    }

    fun getSelectedIndices(): List<Int> = selectedIndices

    fun clearSelection() {
        selectedIndices.clear()
        recyclerAdapter?.notifyDataSetChanged()
    }

    private fun drawWithAttrs(attrs: AttributeSet) {
        context.withStyledAttributes(
            attrs,
            R.styleable.ExpandableSelectionView, 0, 0
        ) {
            val bgDrawable = getDrawable(R.styleable.ExpandableSelectionView_background)
            maxHeight = getLayoutDimension(
                R.styleable.ExpandableSelectionView_maximumHeight,
                maxHeight
            )
            showDividers = getBoolean(
                R.styleable.ExpandableSelectionView_dividerVisibility,
                showDividers
            )
            dividerColor = getColor(
                R.styleable.ExpandableSelectionView_dividerColor,
                dividerColor
            )
            animationDuration = getInteger(
                R.styleable.ExpandableSelectionView_animationDuration,
                animationDuration.toInt()
            ).toLong()

            itemsRecyclerView.maxHeight = maxHeight
            bgDrawable?.let { contentLayout.background = it }
        }
    }

    private fun initState() {
        this.currentState = State.Collapsed
        expandableSelectionAdapter?.bindHeaderView(headerView, selectedIndices)
        expandableSelectionAdapter?.onViewStateChanged(
            headerView,
            currentState
        )
        itemsRecyclerView.isGone = true
    }

    private fun addContentViews(adapter: ExpandableItemAdapter) {
        headerView = adapter.inflateHeaderView(this)
        headerView.setOnClickListener { onHeaderClicked() }
        contentLayout.removeAllViews()
        contentLayout.addView(headerView)
        contentLayout.addView(itemsRecyclerView)
    }

    private fun setRecyclerAdapter(recyclerAdapter: ExpandableItemRecyclerAdapter) {
        this.recyclerAdapter = recyclerAdapter
        val linearLayoutManager = LinearLayoutManager(context)
        itemsRecyclerView.adapter = recyclerAdapter
        itemsRecyclerView.itemAnimator = null
        itemsRecyclerView.layoutManager = linearLayoutManager
    }

    private fun onHeaderClicked() {
        toggleAndSetState()
    }

    private fun toggleAndSetState() {
        when (currentState) {
            is State.Expanded -> collapse()
            is State.Collapsed -> expand()
        }
        currentState = !currentState
        expandableSelectionAdapter?.onViewStateChanged(headerView, currentState)
    }

    private fun expand() {
        itemsRecyclerView.expand(animationDuration)
    }

    private fun collapse() {
        itemsRecyclerView.collapse(animationDuration)
    }

    abstract fun selectIndex(index: Int)

    abstract fun handleItemClick(index: Int)

    protected fun isSelected(index: Int) = selectedIndices.contains(index)

    protected fun selectItem(index: Int) {
        selectedIndices.add(index)
        expandableSelectionAdapter?.bindHeaderView(headerView, selectedIndices)
        recyclerAdapter?.notifyItemChanged(index)
    }

    protected fun unSelectItem(index: Int) {
        selectedIndices.remove(index)
        expandableSelectionAdapter?.bindHeaderView(headerView, selectedIndices)
        recyclerAdapter?.notifyItemChanged(index)
    }

    sealed class State {
        object Expanded : State()
        object Collapsed : State()

        operator fun not(): State =
            when (this) {
                Expanded -> Collapsed
                Collapsed -> Expanded
            }
    }

    companion object {
        private const val DEFAULT_ANIMATION_DURATION_SCALE: Long = 300L
    }
}