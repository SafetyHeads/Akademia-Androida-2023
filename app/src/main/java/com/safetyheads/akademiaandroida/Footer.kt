package com.safetyheads.akademiaandroida

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

// Class responsible for displaying a footer at the bottom of the screen with clickable links
// to the "Privacy policy" and "Cookie policy" pages.
class Footer : Fragment() {
    // Utility function for creating clickable spans
    private fun createClickableSpan(spannableString: SpannableString, start: Int, end: Int, url: String) {
        spannableString.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                requireContext().startActivity(browserIntent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
//Todo: Replace hardcoded values
                ds.color = Color.BLUE
                ds.isUnderlineText = false
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_footer, container, false)

        // Get references to the TextViews
        val textFooterUp = view.findViewById<TextView>(R.id.footerTextUp)
        val textFooterDown = view.findViewById<TextView>(R.id.footerTextDown)
//Todo: Replace hardcoded values
        // Set the text for the TextViews
        textFooterDown.text = "© 2023 SafetyHeads"
        textFooterUp.text = "SafetyHeads.com - Privacy policy and Cookie policy"

        // Create a SpannableString from the text
        val text = textFooterUp.text
        val spannableString = SpannableString(text)

        // Position of the "Privacy policy" and "Cookie policy" substrings
        val privacyPolicyStart = text.indexOf("Privacy policy")
        val privacyPolicyEnd = privacyPolicyStart + "Privacy policy".length
        val cookiePolicyStart = text.indexOf("Cookie policy")
        val cookiePolicyEnd = cookiePolicyStart + "Cookie policy".length
//Todo: Replace hardcoded values
        // Create clickable spans for the "Privacy policy" and "Cookie policy" substrings
        createClickableSpan(
            spannableString,
            privacyPolicyStart,
            privacyPolicyEnd,
            "https://safetyheads.com/android-academy"
        )
        createClickableSpan(
            spannableString,
            cookiePolicyStart,
            cookiePolicyEnd,
            "https://safetyheads.com/contact-us"
        )

// Set the text of the TextView to the SpannableString and make it clickable
        textFooterUp.text = spannableString
        textFooterUp.movementMethod = LinkMovementMethod.getInstance()
        // Return the inflated view
        return view
    }
}

