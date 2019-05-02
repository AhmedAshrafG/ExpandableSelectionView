package com.ashraf007.expandableselectionview.extension

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import kotlin.math.roundToInt

fun Context.getColorCompat(@ColorRes colorResId: Int) =
        ContextCompat.getColor(this, colorResId)

fun Context.dpToPixels(dp: Int) =
    (dp * screenDensity).roundToInt()

val Context.screenDensity
    get() = resources.displayMetrics.density