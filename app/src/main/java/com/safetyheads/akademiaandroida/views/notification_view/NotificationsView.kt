package com.safetyheads.akademiaandroida.views.notification_view

import android.animation.LayoutTransition
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
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


    fun initOnClickListeners() {
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

    fun setText(resIdText: Int) {
        binding.titleText.text = resources.getString(resIdText)
    }

    fun switchButtonListener(x: (isChecked: Boolean) -> Any) {
        binding.switchButton.setOnCheckedChangeListener { _, isChecked ->
            x(isChecked)
        }
    }

    fun setSwitchButton(value: Boolean) {
        binding.switchButton.isChecked = value
    }
}