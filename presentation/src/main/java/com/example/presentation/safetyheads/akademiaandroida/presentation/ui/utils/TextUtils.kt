package com.example.presentation.safetyheads.akademiaandroida.presentation.ui.utils

import android.content.Intent
import android.net.Uri
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

// Utility class suitable for creating colored and clickable spans.
object TextUtils {
    // This fun creates a SpannableString with specified colors and links applied
    // to specific substrings.
    fun createColoredLinks(
        text: String,
        pairs: List<Pair<String, String>>,
        color: Int
    ): SpannableString {
        val spannableString = SpannableString(text)

        pairs.forEach { pair ->
            val start = text.indexOf(pair.first)
            val end = start + pair.first.length
            val url = pair.second
            // Only create the span if the substring is present in the text
            if (start > -1) {
                spannableString.setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        widget.context.startActivity(browserIntent)
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.color = color
                        ds.isUnderlineText = false
                    }
                }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

        return spannableString
    }
}
