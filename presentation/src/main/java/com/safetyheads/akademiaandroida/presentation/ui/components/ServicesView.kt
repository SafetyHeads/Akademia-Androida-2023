package com.safetyheads.akademiaandroida.presentation.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.safetyheads.akademiaandroida.presentation.databinding.ViewServicesBinding

class ServicesView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): FrameLayout(context, attrs, defStyle) {

    private val binding = ViewServicesBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    fun setText(resIdText1: Int, resIdText2: Int, resIdText3: Int) {
        binding.textView1.text = resources.getString(resIdText1)
        binding.textView2.text = resources.getString(resIdText2)
        binding.textView3.text = resources.getString(resIdText3)
    }

    fun setImage(resIdImage: Int) {
        binding.imageView.setImageResource(resIdImage)
    }
}
