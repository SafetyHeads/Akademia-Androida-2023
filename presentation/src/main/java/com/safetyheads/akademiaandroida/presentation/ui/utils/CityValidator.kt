package com.safetyheads.akademiaandroida.presentation.ui.utils

import android.content.Context
import android.text.InputFilter
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.safetyheads.akademiaandroida.presentation.R


object CityValidator {
    private const val MAX_TEXT_LENGTH = 30
    private const val CITY_PATTERN = "^[A-Za-z\\s]+$"
    var IS_CORRECT = false

    fun attach(editText: EditText, context: Context) {
        val lengthFilter = InputFilter.LengthFilter(MAX_TEXT_LENGTH)
        editText.filters = arrayOf(lengthFilter)
        editText.addTextChangedListener { text ->
            val address = text.toString().trim()
            if (validateCity(address, editText, context)) {
                editText.error = null
                IS_CORRECT = true
            } else {
                IS_CORRECT = false
            }
        }
    }

    private fun validateCity(
        address: String,
        editText: EditText,
        context: Context
    ): Boolean {
        var isCorrect = true

        if (!Regex(CITY_PATTERN).matches(address) || address.length < 3) {
            editText.error = context.getString(R.string.invalid_city)
            isCorrect = false
        }

        return isCorrect
    }
}
