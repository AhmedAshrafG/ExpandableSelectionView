package com.ashraf007.selection_view_sample

import android.content.Context
import kotlin.math.roundToInt

fun Context.dpToPixels(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale).roundToInt()
}