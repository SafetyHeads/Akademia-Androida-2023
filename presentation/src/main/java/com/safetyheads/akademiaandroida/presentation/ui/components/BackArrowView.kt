package com.safetyheads.akademiaandroida.presentation.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import com.safetyheads.akademiaandroida.presentation.databinding.ViewArrowBinding

class BackArrowView : FrameLayout {

    private lateinit var binding: ViewArrowBinding

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

    private fun initView() {
        binding = ViewArrowBinding.inflate(LayoutInflater.from(context), this)
//        binding.imageButton.setOnClickListener()
    }

    fun setImage(@DrawableRes imageRes: Int) {
        binding.imageButton.setImageResource(imageRes)
    }

    fun customButtonListener(callbackFunction: () -> Any) {
        binding.imageButton.setOnClickListener {
            callbackFunction()
        }
    }
}
