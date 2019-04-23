package com.ashraf007.expandableselectionview

import androidx.databinding.BindingAdapter

@BindingAdapter("errorText")
fun ExpandableSelectionView.setErrorText(errorText: String?) {
    this.error = errorText
}

@BindingAdapter(value = ["itemList", "hint", "nothingSelectedCallback"], requireAll = false)
fun ExpandableSelectionView.setItemList(
    itemList: List<String>?,
    hint: String?,
    nothingSelectedCallback: NothingSelectedCallback?
) {
    this.nothingSelectedListener = { nothingSelectedCallback?.onNothingSelected() }
    if (itemList != null)
        this.expandableSelectionAdapter = StringAdapter(itemList, hint)
}

@BindingAdapter("selectionCallback")
fun SingleExpandableSelectionView.setSelectionCallback(callback: SingleSelectionCallback?) {
    this.selectionListener = { callback?.onItemSelected(it) }
}

@BindingAdapter("mulitselectionCallback")
fun MultiExpandableSelectionView.setMulitselectionCallback(
    callback: MultiExpandableSelectionView
    .MultiSelectionCallback?
) {
    this.selectionListener = { callback?.onItemsSelected(it) }
}