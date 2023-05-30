package com.safetyheads.akademiaandroida.presentation.ui.utils

import android.content.Context
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.safetyheads.akademiaandroida.presentation.R

object PhoneNumberValidator {
    private val phoneNumberRegex = "^(\\+\\d{2}[- ]?)?\\d{9}\$".toRegex()
    var IS_CORRECT = false

    fun attach(editText: EditText, context: Context) {
        editText.addTextChangedListener {
            validatePhoneNumber(it.toString(), editText, context)
        }
    }

    fun validatePhoneNumber(phoneNumber: String, editText: EditText, context: Context) {
        if (!isValid(phoneNumber)) {
            editText.error = context.getString(R.string.invalid_phone_message)
            IS_CORRECT = false
        } else {
            editText.error = null
            IS_CORRECT = true
        }
    }

    fun isValid(phoneNumber: String): Boolean {
        return phoneNumberRegex.matches(phoneNumber)
    }

}
