package com.safetyheads.akademiaandroida

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

object PhoneNumberValidator {
    private val phoneNumberRegex = "^(\\+\\d{2}[- ]?)?\\d{9}\$".toRegex()

    fun attach(editText: EditText, context: Context) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validatePhoneNumber(s.toString(), editText, context)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePhoneNumber(s.toString(), editText, context)
            }
        })
    }

    private fun isValid(phoneNumber: String): Boolean {
        return phoneNumberRegex.matches(phoneNumber)
    }

    private fun validatePhoneNumber(phoneNumber: String, editText: EditText, context: Context) {
        if (!isValid(phoneNumber)) {
            editText.error = context.getString(R.string.invalid_phone_message)
        } else {
            editText.error = null
        }
    }
}
