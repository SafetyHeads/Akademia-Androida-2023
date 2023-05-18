package com.safetyheads.akademiaandroida.presentation.ui.utils

import android.content.Context
import android.text.InputFilter
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.safetyheads.akademiaandroida.presentation.R


object CityValidator {
    private const val MAX_TEXT_LENGTH = 100
    private const val ZIPCODE_PATTERN = "^\\d{2}-\\d{3}|\\d{5}$"
    private const val CITY_PATTERN = "^[A-Za-z\\s]+$"

    fun attach(editText: EditText, context: Context) {
        val lengthFilter = InputFilter.LengthFilter(MAX_TEXT_LENGTH)
        editText.filters = arrayOf(lengthFilter)
        editText.addTextChangedListener { text ->
            val address = text.toString().trim()
            val splitAddress = address.split(" ")
            if (validateCity(splitAddress, editText, context)) {
                editText.error = null
            }
        }
    }

    fun validateCity(
        splitAddress: List<String>,
        editText: EditText,
        context: Context
    ): Boolean {
        var isCorrect = true

        if (splitAddress.size != 2) {
            editText.error = context.getString(R.string.invalid_address_message)
            isCorrect = false
        } else {
            val zipCode = splitAddress[0]
            val city = splitAddress[1]

            if (!Regex(ZIPCODE_PATTERN).matches(zipCode)) {
                editText.error = context.getString(R.string.invalid_zip_code)
                isCorrect = false
            }

            if (!Regex(CITY_PATTERN).matches(city) || city.length < 3) {
                editText.error = context.getString(R.string.invalid_city)
                isCorrect = false
            }

        }
        return isCorrect
    }
}
