package com.vic_project.search_image.utils.extensions

import android.annotation.TargetApi
import android.graphics.Typeface
import android.os.Build
import android.text.TextPaint
import android.text.style.MetricAffectingSpan
import android.text.style.TypefaceSpan

fun Typeface.getTypefaceSpan(): MetricAffectingSpan =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        typefaceSpanCompatV28(this)
    } else {
        CustomTypefaceSpan(this)
    }

@TargetApi(Build.VERSION_CODES.P)
private fun typefaceSpanCompatV28(typeface: Typeface) = TypefaceSpan(typeface)

private class CustomTypefaceSpan(private val typeface: Typeface?) : MetricAffectingSpan() {

    override fun updateDrawState(paint: TextPaint) {
        paint.typeface = typeface
    }

    override fun updateMeasureState(paint: TextPaint) {
        paint.typeface = typeface
    }
}