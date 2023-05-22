package com.safetyheads.akademiaandroida.presentation.ui.utils

import android.content.Context
import android.text.InputFilter
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.safetyheads.akademiaandroida.presentation.R

object StreetNumberValidator {
    private const val MAX_TEXT_LENGTH = 6
    private const val MIN_NUMBER_LENGTH = 1
    var IS_CORRECT = false

    fun attach(editText: EditText, context: Context) {
        val lengthFilter = InputFilter.LengthFilter(MAX_TEXT_LENGTH)
        editText.filters = arrayOf(lengthFilter)
        editText.addTextChangedListener { text ->
            val streetNumber = text.toString().trim()
            var checkout = true

            if (validateStreetNumber(streetNumber, editText, context) && checkout) {
                editText.error = null
                IS_CORRECT = true
            } else {
                IS_CORRECT = false
            }
        }
    }

    private fun validateStreetNumber(
        streetNumber: String,
        editText: EditText,
        context: Context
    ): Boolean {
        var isCorrect = true

        if (streetNumber.length < MIN_NUMBER_LENGTH
            || streetNumber.endsWith("/")
            || streetNumber.length > MAX_TEXT_LENGTH) {
            editText.error = context.getString(R.string.invalid_house_number_message)
            isCorrect = false
        }

        return isCorrect
    }
}