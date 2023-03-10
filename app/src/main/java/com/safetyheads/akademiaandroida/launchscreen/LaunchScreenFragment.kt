package com.safetyheads.akademiaandroida.launchscreen
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.R
import com.safetyheads.akademiaandroida.Footer
import com.safetyheads.akademiaandroida.databinding.FragmentLaunchScreenBinding

class LaunchScreenFragment : Fragment() {
    private lateinit var binding: FragmentLaunchScreenBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLaunchScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val welcomeMessage = binding.txtWelcome
        val welcomeMessageTxT = welcomeMessage.text

        val shColor = ContextCompat.getColor(requireContext(), R.color.p_60)
        val sfStart = welcomeMessageTxT.indexOf(resources.getString(R.string.launch_screen_txt_company_name))
        val sfEnd = sfStart + resources.getString(R.string.launch_screen_txt_company_name).length

        val spannable: Spannable = SpannableString(welcomeMessage.text)
        spannable.setSpan(ForegroundColorSpan(shColor), sfStart, sfEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        welcomeMessage.setText(spannable, TextView.BufferType.SPANNABLE)

        childFragmentManager.beginTransaction()
            .add(R.id.parent_layout, Footer())
            .commit()
    }
}

