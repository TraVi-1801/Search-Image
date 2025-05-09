package com.vic_project.search_image.utils.compose

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.TextPaint
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import com.vic_project.search_image.R
import com.vic_project.search_image.utils.extensions.getTypefaceSpan

fun fromHtml(context: Context, html: String): Spannable = parse(html).apply {
    removeLinksUnderline()
    styleBold(context)
}
 fun parse(html: String): Spannable =
    (HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT) as Spannable)

 fun Spannable.removeLinksUnderline() {
    for (s in getSpans(0, length, URLSpan::class.java)) {
        setSpan(object : UnderlineSpan() {
            override fun updateDrawState(tp: TextPaint) {
                tp.isUnderlineText = false
            }
        }, getSpanStart(s), getSpanEnd(s), 0)
    }
}

 fun Spannable.styleBold(context: Context) {
    val bold = ResourcesCompat.getFont(context, R.font.space_grotesk_bold)!!
    for (s in getSpans(0, length, StyleSpan::class.java)) {
        if (s.style == Typeface.BOLD) {
            setSpan(ForegroundColorSpan(Color.BLACK), getSpanStart(s), getSpanEnd(s), 0)
            setSpan(bold.getTypefaceSpan(), getSpanStart(s), getSpanEnd(s), 0)
        }
    }
}