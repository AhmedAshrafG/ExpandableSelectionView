package com.ashraf007.selection_view_sample.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashraf007.expandableselectionview.R
import com.ashraf007.selection_view_sample.adapter.DefaultAdapter
import com.ashraf007.selection_view_sample.adapter.ExpandableItemAdapter
import com.ashraf007.selection_view_sample.adapter.ExpandableItemRecyclerAdapter
import com.ashraf007.selection_view_sample.dpToPixels

public abstract class ExpandableSelectionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val inflater: LayoutInflater by lazy { LayoutInflater.from(context) }
    private lateinit var headerView: View
    private val itemsRecyclerView: MaxHeightRecyclerView
    private val errorLabel: TextView
    private val contentLayout: LinearLayout

    private var showDividers: Boolean = true
    private var maxHeight: Int = Int.MAX_VALUE
    private var currentState: State = State.Collapsed
    private var selectedIndices: MutableList<Int> = mutableListOf()

    public var error: String? = null
        set(value) {
            if (value == null) {
                errorLabel.visibility = View.GONE
            } else {
                errorLabel.visibility = View.VISIBLE
            }
            errorLabel.text = value
            field = value
        }
    public var expandableSelectionAdapter: ExpandableItemAdapter =
        DefaultAdapter()
        set(value) {
            field = value
            recyclerAdapter.adapter = value
            if (contentLayout.childCount > 0) {
                selectedIndices.clear()
                currentState = State.Collapsed
                nothingSelectedListener?.invoke()
            } else {
                initViews()
            }
            initState()
            recyclerAdapter.notifyDataSetChanged()
        }

    private val recyclerAdapter: ExpandableItemRecyclerAdapter by lazy {
        ExpandableItemRecyclerAdapter(
            expandableSelectionAdapter,
            showDividers,
            this::handleItemClick,
            this::isSelected
        )
    }

    public var nothingSelectedListener: (() -> Unit)? = null

    init {
        orientation = VERTICAL
        val padding = context.dpToPixels(VIEW_PADDING)

        val viewGroup = rootView as ViewGroup
        contentLayout = LinearLayout(context)

        itemsRecyclerView =
                inflater.inflate(R.layout.expandable_recycler_view, viewGroup, false) as MaxHeightRecyclerView
        errorLabel = inflater.inflate(R.layout.error_label, viewGroup, false) as TextView

        contentLayout.orientation = VERTICAL
        contentLayout.setPadding(padding, padding, padding, padding)

        this.addView(contentLayout)
        this.addView(errorLabel)

        attrs?.let { drawWithAttrs(it) }

        initRecyclerView()
    }

    private fun initViews() {
        val viewGroup = rootView as ViewGroup
        headerView = expandableSelectionAdapter.getHeaderView(inflater, viewGroup)
        headerView.setOnClickListener { onHeaderClicked() }
        contentLayout.addView(headerView)
        contentLayout.addView(itemsRecyclerView)
    }

    private fun initState() {
        expandableSelectionAdapter.bindSelectedItem(headerView, selectedIndices)
        expandableSelectionAdapter.onExpandableStateChanged(headerView,
            State.Collapsed
        )
        collapse()
    }

    private fun initRecyclerView() {
        itemsRecyclerView.adapter = recyclerAdapter
        val linearLayoutManager = LinearLayoutManager(context)
        itemsRecyclerView.layoutManager = linearLayoutManager
        itemsRecyclerView.itemAnimator = null
    }

    private fun drawWithAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs,
            R.styleable.ExpandableSelectionView, 0, 0)
        val bgDrawable = typedArray.getDrawable(R.styleable.ExpandableSelectionView_background)
        maxHeight = typedArray.getLayoutDimension(R.styleable.ExpandableSelectionView_maximumHeight, maxHeight)
        showDividers = typedArray.getBoolean(R.styleable.ExpandableSelectionView_dividerVisibility, showDividers)
        itemsRecyclerView.maxHeight = maxHeight

        bgDrawable?.let { contentLayout.background = it }

        typedArray.recycle()
    }

    private fun onHeaderClicked() {
        toggleAndSetState()
    }

    public fun setState(state: State) {
        if (currentState == state)
            return
        toggleAndSetState()
    }

    private fun toggleAndSetState() {
        when (currentState) {
            is State.Expanded -> collapse()
            is State.Collapsed -> expand()
        }
        currentState = !currentState
        expandableSelectionAdapter.onExpandableStateChanged(headerView, currentState)
    }

    private fun expand() {
        itemsRecyclerView.visibility = View.VISIBLE
    }

    private fun collapse() {
        itemsRecyclerView.visibility = View.GONE
    }

    public abstract fun selectIndex(index: Int)

    abstract fun handleItemClick(index: Int)

    public fun getSelectedIndices() = selectedIndices

    protected fun isSelected(index: Int) = selectedIndices.contains(index)

    protected fun selectItem(index: Int) {
        selectedIndices.add(index)
        expandableSelectionAdapter.bindSelectedItem(headerView, selectedIndices)
        recyclerAdapter.notifyItemChanged(index)
    }

    protected fun unSelectItem(index: Int) {
        selectedIndices.remove(index)
        expandableSelectionAdapter.bindSelectedItem(headerView, selectedIndices)
        recyclerAdapter.notifyItemChanged(index)
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
        private const val VIEW_PADDING: Int = 2
        private const val ANIMATION_DURATION: Long = 50
    }
}