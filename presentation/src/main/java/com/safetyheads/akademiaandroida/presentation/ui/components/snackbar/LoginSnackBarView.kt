package com.safetyheads.akademiaandroida.presentation.ui.components.snackbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.ContentViewCallback
import com.safetyheads.akademiaandroida.R

class LoginSnackBarView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defaultStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defaultStyle), ContentViewCallback {
    var textView: TextView
    var closeIcon: ImageView

    init {
        View.inflate(context, R.layout.snackbar_login, this)
        clipToPadding = false
        this.textView = findViewById(R.id.text_login_snackbar)
        this.closeIcon = findViewById(R.id.close_icon_login_snackbar)
    }

    override fun animateContentIn(delay: Int, duration: Int) {}

    override fun animateContentOut(delay: Int, duration: Int) {}
}