package com.example.presentation.safetyheads.akademiaandroida.presentation.ui.components

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.R
import com.safetyheads.akademiaandroida.databinding.FragmentFooterBinding
import com.safetyheads.akademiaandroida.presentation.ui.utils.TextUtils

class Footer : Fragment() {

    private lateinit var binding: FragmentFooterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFooterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.footerTextDown.text = resources.getString(R.string.footer_text_down)
        binding.footerTextUp.text = resources.getString(R.string.footer_text_up)
        val poppinsRegular = context?.let { ResourcesCompat.getFont(it, R.font.poppins_regular) }
        binding.footerTextUp.typeface = poppinsRegular
        binding.footerTextDown.typeface = poppinsRegular
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
                    binding.footerTextUp.text.toString(), pairList, it.getColor(R.color.p_60)
                )
            }
        binding.footerTextUp.text = footerUpText
        binding.footerTextUp.movementMethod = LinkMovementMethod.getInstance()
    }
}
