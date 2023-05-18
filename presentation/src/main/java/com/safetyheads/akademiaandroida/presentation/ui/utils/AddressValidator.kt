package com.safetyheads.akademiaandroida.presentation.ui.utils

import android.content.Context
import android.text.InputFilter
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.safetyheads.akademiaandroida.presentation.R

object AddressValidator {
    private const val MAX_TEXT_LENGTH = 100
    private const val MIN_STREET_LENGTH = 3
    private const val MIN_HOUSE_NUMBER_LENGTH = 1
    private const val MIN_APARTMENT_NUMBER_LENGTH = 1
    var IS_CORRECT = false

    fun attach(editText: EditText, context: Context) {
        var splitResult = mutableListOf<String>()
        val lengthFilter = InputFilter.LengthFilter(MAX_TEXT_LENGTH)
        editText.filters = arrayOf(lengthFilter)
        editText.addTextChangedListener { text ->
            var checkout = true
            val address = text.toString().trim()
            val splitAddress = address.split(" ")
            if (splitAddress.size == 2) {
                if (splitAddress[1].endsWith("/"))
                    checkout = false
                else {
                    val splitNumber = splitAddress[1].split("/")
                    if (splitNumber.size == 2) {
                        if (splitNumber[0].isNotEmpty() && splitNumber[1].isNotEmpty())
                            splitResult =
                                mutableListOf(splitAddress[0], splitNumber[0], splitNumber[1])
                        else if (splitNumber[0].isNotEmpty())
                            splitResult = mutableListOf(splitAddress[0], splitNumber[0])
                        else
                            checkout = false
                    } else if (splitNumber.size == 1) {
                        splitResult = mutableListOf(splitAddress[0], splitNumber[0])
                    } else
                        checkout = false
                }
            }

            if (validateAddress(splitResult, editText, context) && checkout) {
                editText.error = null
                IS_CORRECT = true
            } else {
                IS_CORRECT = false
            }
        }
    }

    private fun validateAddress(
        splitAddress: List<String>,
        editText: EditText,
        context: Context
    ): Boolean {
        var isCorrect = true

        if (splitAddress.size < 2 || splitAddress.size > 3) {
            editText.error = context.getString(R.string.invalid_address_message)
            isCorrect = false
        } else {
            val street = splitAddress[0]
            val houseNumber = splitAddress[1]

            if (street.length < MIN_STREET_LENGTH || !street[0].isUpperCase()) {
                editText.error = context.getString(R.string.invalid_street_message)
                isCorrect = false
            }

            if (houseNumber.length < MIN_HOUSE_NUMBER_LENGTH) {
                editText.error = context.getString(R.string.invalid_house_number_message)
                isCorrect = false
            }

            if (splitAddress.size == 3) {
                val apartmentNumber = splitAddress[2]
                if (apartmentNumber.length < MIN_APARTMENT_NUMBER_LENGTH) {
                    editText.error = context.getString(R.string.invalid_apartment_number_message)
                    isCorrect = false
                }
            }
        }

        return isCorrect
    }
}
