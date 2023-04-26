package com.safetyheads.akademiaandroida.views.notificationview

import android.animation.LayoutTransition
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.safetyheads.akademiaandroida.R
import com.safetyheads.akademiaandroida.databinding.ViewNotificationsBinding


class NotificationsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): FrameLayout(context, attrs, defStyle) {

    private val binding = ViewNotificationsBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )
    private val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.NotificationsView)
    init {
        binding.titleText.text = attributes.getString(R.styleable.NotificationsView_title_text)

        binding.arrow.setOnClickListener {
            val contentVisibility = binding.expandableContent.visibility
            if(contentVisibility == View.GONE) {
                binding.linearLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
                binding.arrow.rotation = 180f
                binding.expandableContent.visibility = View.VISIBLE

            } else {
                binding.arrow.rotation = 0f
                binding.expandableContent.visibility = View.GONE
            }
        }
    }


//    fun initOnClickListeners() {
//
//    }

    fun switchButtonListener(callbackFunction: (isChecked: Boolean) -> Any) {
        binding.switchButton.setOnCheckedChangeListener { _, isChecked ->
            callbackFunction(isChecked)
        }
    }

    fun setSwitchButton(value: Boolean) {
        binding.switchButton.isChecked = value
    }
}
