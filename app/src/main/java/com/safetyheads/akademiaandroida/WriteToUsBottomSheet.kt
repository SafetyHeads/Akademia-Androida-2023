package com.safetyheads.akademiaandroida

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.safetyheads.akademiaandroida.utils.TextUtils

// example usage
// val bottomSheetFragment = WriteToUsBottomSheet()
// bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)

class WriteToUsBottomSheet : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textPrivacyPolicy = view.findViewById<TextView>(R.id.txt_privacy_policy)

        val pairList = listOf(
            Pair(
                "Privacy policy",
                resources.getString(R.string.privacy_policy_url)
            )
        )

        val formattedText =
            context?.let {
                TextUtils.createColoredLinks(
                    textPrivacyPolicy.text.toString(), pairList, it.getColor(R.color.p_60)
                )
            }
        textPrivacyPolicy.text = formattedText
        textPrivacyPolicy.movementMethod = LinkMovementMethod.getInstance()

        val bottomSheet = view.parent as View
        bottomSheet.backgroundTintMode = PorterDuff.Mode.CLEAR
        bottomSheet.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
        (dialog as BottomSheetDialog).behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}