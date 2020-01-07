package com.ashraf007.expandableselectionview.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashraf007.expandableselectionview.R
import com.ashraf007.expandableselectionview.adapter.BasicStringAdapter
import com.ashraf007.expandableselectionview.adapter.ExpandableItemAdapter
import com.ashraf007.expandableselectionview.adapter.ExpandableItemRecyclerAdapter
import com.ashraf007.expandableselectionview.extension.inflate
import java.io.InvalidObjectException

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
    private var bgDrawable: Drawable? = getDrawable(context, R.drawable.bg_expandable_selection_view)
    private var showScrollBars: Boolean = true
    private var showDividers: Boolean = true
    private var dividerColor: Int = Color.parseColor("#668b999f")
    private var maxHeight: Int = Int.MAX_VALUE
    private var animationDuration: Long = DEFAULT_ANIMATION_DURATION
    private var selectedIndices: MutableList<Int> = mutableListOf()
    private var entriesAttribute: Int? = null
    private var hintString: CharSequence? = null

    private var expandableSelectionAdapter: ExpandableItemAdapter? = null
    private var recyclerAdapter: ExpandableItemRecyclerAdapter? = null

    init {
        attrs?.let { extractAttributes(it) }

        this.orientation = VERTICAL

        contentLayout = LinearLayout(context)
        contentLayout.orientation = VERTICAL
        contentLayout.background = bgDrawable

        val recyclerStyle = when {
            showScrollBars -> R.style.ExpandableRecyclerView_Scrollbars
            else -> R.style.ExpandableRecyclerView
        }
        itemsRecyclerView =
            ExpandableRecyclerView(ContextThemeWrapper(context, recyclerStyle))
        itemsRecyclerView.maxHeight = maxHeight

        errorLabel = inflate(R.layout.error_field_layout) as TextView
        headerView = View(context)

        this.addView(contentLayout)
        this.addView(errorLabel)

        if (entriesAttribute != 0) {

            val mItemList: List<String> = listOf(
                *context.resources.getStringArray(
                    entriesAttribute!!
                )
            )

            val adapter = BasicStringAdapter(mItemList, hintString?.toString())

            setAdapter(adapter)
        } else if (hintString != null) {
            throw InvalidObjectException("'hint' text can only be supplied when 'entries' are included.")
        }
    }

    fun setAdapter(adapter: ExpandableItemAdapter) {
        this.expandableSelectionAdapter = adapter
        val recyclerAdapter = ExpandableItemRecyclerAdapter(
            adapter,
            ::handleItemClick,
            ::isSelected
        ).also {
            it.showDividers = showDividers
            it.dividerColor = ResourcesCompat.getColor(resources, R.color.text_color, null)
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

    internal fun getSelectedIndices(): List<Int> = selectedIndices

    open fun clearSelection() {
        selectedIndices.clear()
        expandableSelectionAdapter?.bindHeaderView(headerView, selectedIndices)
        recyclerAdapter?.notifyDataSetChanged()
    }

    private fun extractAttributes(attrs: AttributeSet) {


        context.withStyledAttributes(
            attrs,
            R.styleable.ExpandableSelectionView, 0, 0
        ) {

            bgDrawable = getDrawable(R.styleable.ExpandableSelectionView_android_background)
                ?: bgDrawable
            maxHeight = getLayoutDimension(
                R.styleable.ExpandableSelectionView_maximumHeight,
                maxHeight
            )
            showDividers = getBoolean(
                R.styleable.ExpandableSelectionView_dividerVisibility,
                showDividers
            )
            showScrollBars = getBoolean(
                R.styleable.ExpandableSelectionView_scrollBarsVisibility,
                showScrollBars
            )
            dividerColor = getColor(
                R.styleable.ExpandableSelectionView_dividerColor,
                dividerColor
            )
            animationDuration = getInteger(
                R.styleable.ExpandableSelectionView_animationDuration,
                animationDuration.toInt()
            ).toLong()
            entriesAttribute = attrs.getAttributeResourceValue(
                R.styleable.ExpandableSelectionView_android_entries,
                0
            )
            hintString = getText(R.styleable.ExpandableSelectionView_android_hint)
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
        itemsRecyclerView.scrollToPosition(0)
        itemsRecyclerView.expand(animationDuration)
    }

    private fun collapse() {
        itemsRecyclerView.collapse(animationDuration)
    }

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
        private const val DEFAULT_ANIMATION_DURATION: Long = 300L
    }
}