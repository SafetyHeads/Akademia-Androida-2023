package com.safetyheads.akademiaandroida.presentation.ui.utils

import android.widget.EditText
import androidx.core.util.PatternsCompat
import androidx.core.widget.addTextChangedListener
import com.safetyheads.akademiaandroida.R

object EmailValidator {
    fun attach(editText: EditText) {
        editText.addTextChangedListener {
            validateEmail(it.toString(), editText)
        }
    }

    fun validateEmail(email: String, editText: EditText) {
        if (!isValid(email)) {
            editText.error = editText.context.getString(R.string.invalid_email)
        } else {
            editText.error = null
        }
    }

    fun isValid(email: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }

}