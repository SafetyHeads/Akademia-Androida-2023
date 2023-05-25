package com.safetyheads.akademiaandroida.presentation.ui.utils

import android.content.Context
import android.text.InputFilter
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.safetyheads.akademiaandroida.presentation.R

object ZipCodeValidator {
    private const val MAX_TEXT_LENGTH = 6
    private const val ZIPCODE_PATTERN = "^\\d{2}-\\d{3}|\\d{5}$"
    var IS_CORRECT = false

    fun attach(editText: EditText, context: Context) {
        val lengthFilter = InputFilter.LengthFilter(MAX_TEXT_LENGTH)
        editText.filters = arrayOf(lengthFilter)
        editText.addTextChangedListener { text ->
            val zipCode = text.toString().trim()
            if (validateZipCode(zipCode, editText, context)) {
                editText.error = null
                IS_CORRECT = true
            } else {
                IS_CORRECT = false
            }
        }
    }

    private fun validateZipCode(
        zipCode: String,
        editText: EditText,
        context: Context
    ): Boolean {
        var isCorrect = true

        if (!Regex(ZIPCODE_PATTERN).matches(zipCode)) {
            editText.error = context.getString(R.string.invalid_zip_code)
            isCorrect = false
        }

        return isCorrect
    }

}