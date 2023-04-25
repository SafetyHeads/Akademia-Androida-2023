package com.example.presentation.safetyheads.akademiaandroida.presentation.ui.components.snackbar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.safetyheads.akademiaandroida.R

class LoginSnackBar(parent: ViewGroup, content: LoginSnackBarView) :
    BaseTransientBottomBar<LoginSnackBar>(parent, content, content) {

    init {
        getView().setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                android.R.color.transparent
            )
        )
        content.closeIcon.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        fun make(view: View, message: String): LoginSnackBar {
            val parent =
                view.findSuitableParent() ?: throw IllegalArgumentException("No parent found.")
            val layoutSnackbar = LayoutInflater.from(view.context)
                .inflate(R.layout.layout_snackbar_login, parent, false) as LoginSnackBarView
            layoutSnackbar.textView.text = message
            return LoginSnackBar(parent, layoutSnackbar).setDuration(Snackbar.LENGTH_LONG)
        }
    }
}