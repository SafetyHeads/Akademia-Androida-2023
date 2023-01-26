package com.safetyheads.akademiaandroida

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.utils.TextUtils

class Footer : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_footer, container, false)
        val textFooterUp = view.findViewById<TextView>(R.id.footerTextUp)
        val textFooterDown = view.findViewById<TextView>(R.id.footerTextDown)
        textFooterDown.text = resources.getString(R.string.footer_text_down)
        textFooterUp.text = resources.getString(R.string.footer_text_up)
        val poppinsRegular = context?.let { ResourcesCompat.getFont(it, R.font.poppins_regular) }
        textFooterUp.typeface = poppinsRegular
        textFooterDown.typeface = poppinsRegular
        // Create a list of pairs for the substrings to be colored and linked
        val pairList = listOf(
            Pair(
                resources.getString(R.string.privacy_policy),
                resources.getString(R.string.privacy_policy_url)
            ),
            Pair(
                resources.getString(R.string.cookie_policy),
                resources.getString(R.string.cookie_policy_url)
            )
        )

        val footerUpText =
            context?.let {
                TextUtils.createColoredLinks(
                    textFooterUp.text.toString(), pairList, it.getColor(R.color.p_60)
                )
            }
        textFooterUp.text = footerUpText
        textFooterUp.movementMethod = LinkMovementMethod.getInstance()

        // Return the inflated view
        return view
    }
}
