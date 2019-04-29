package com.ashraf007.selection_view_sample.extension

import android.content.Context
import kotlin.math.roundToInt

fun Context.dpToPixels(dp: Int) =
    (dp * screenDensity).roundToInt()

val Context.screenDensity
    get() = resources.displayMetrics.density