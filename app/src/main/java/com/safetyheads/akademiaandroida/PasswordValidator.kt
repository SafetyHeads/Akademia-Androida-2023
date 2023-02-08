package com.safetyheads.akademiaandroida

import android.content.Context
import android.widget.EditText
import androidx.core.widget.addTextChangedListener

/*
To use this validator you have to add to your code:

      val editTextPassword = findViewById<EditText>(R.id.edit)
        PasswordValidator.attach(editTextPassword, this)
 */
class PasswordValidator {
    private val PasswordRegex =
        Regex("[a-zA-Z0-9][a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]{11,}$")

    fun attach(editText: EditText, context: Context) {
        editText.addTextChangedListener {
            validatePassword(it.toString(), editText, context)
        }
    }

    private fun validatePassword(password: String, editText: EditText, context: Context) {
        if (!isValid(password)) {
            editText.error = context.getString(R.string.invalid_password)
        } else {
            editText.error = null
        }
    }

    private fun isValid(password: String): Boolean {
        return PasswordRegex.matches(password)
    }
}