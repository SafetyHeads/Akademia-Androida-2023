package com.safetyheads.akademiaandroida

import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.utils.TextUtils

class Footer : Fragment() {
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
//Todo: replace hardcoded values of footer texts
        // Set the text for the TextViews
        textFooterDown.text = "© 2023 SafetyHeads"
        textFooterUp.text = "SafetyHeads.com - Privacy policy and Cookie policy"
//Todo: replace hardcoded values of links
        // Create a list of pairs for the substrings to be colored and linked
        val pairList = listOf(
            Pair("Privacy policy", "https://safetyheads.com/android-academy"),
            Pair("Cookie policy", "https://safetyheads.com/contact-us")
        )
//Todo: replace hardcoded values of color
        // Create a SpannableString with the specified colors and specified hyperlinks
        val spannableString = TextUtils().createColoredLinks(textFooterUp.text.toString(), pairList, Color.BLUE)

        // Set the text of the TextView to the SpannableString and make it clickable
        textFooterUp.text = spannableString
        textFooterUp.movementMethod = LinkMovementMethod.getInstance()

        // Return the inflated view
        return view
    }
}
