package com.safetyheads.akademiaandroida.presentation.ui.utils

import android.content.Context
import android.text.InputFilter
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.safetyheads.akademiaandroida.presentation.R

object StreetNameValidator {
    private const val MAX_TEXT_LENGTH = 30
    private const val STREET_NAME_PATTERN = "^[A-Za-z\\s]+$"
    var IS_CORRECT = false

    fun attach(editText: EditText, context: Context) {
        val lengthFilter = InputFilter.LengthFilter(MAX_TEXT_LENGTH)
        editText.filters = arrayOf(lengthFilter)
        editText.addTextChangedListener { text ->
            val streetName = text.toString().trim()
            if (validateCity(streetName, editText, context)) {
                editText.error = null
                IS_CORRECT = true
            } else {
                IS_CORRECT = false
            }
        }
    }

    private fun validateCity(
        streetName: String,
        editText: EditText,
        context: Context
    ): Boolean {
        var isCorrect = true

        if (!Regex(STREET_NAME_PATTERN).matches(streetName)
            || streetName.length < 3
            || !streetName.first().isUpperCase()) {
            editText.error = context.getString(R.string.invalid_street_name)
            isCorrect = false
        }

        return isCorrect
    }
}