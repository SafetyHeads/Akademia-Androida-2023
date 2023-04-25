package com.example.presentation.safetyheads.akademiaandroida.presentation.ui.utils

import android.content.Context
import android.text.InputFilter
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.safetyheads.akademiaandroida.R

/*
* To use this validator you have to add to your code:
* FullNameValidator.attach(fullNameEditText, this)
*/

object FullNameValidator {

    private const val MAX_TEXT_LENGTH = 100
    private const val MIN_NAME_AND_SURNAME_LENGTH = 2
    private const val MIN_SPACE_AMOUNT = 1
    fun attach(editText: EditText, context: Context) {
        val regexFilter =
            InputFilter { source, start, end, _, _, _ ->
                for (i in start until end) {
                    if (!Character.isLetter(source[i]) && source[i] != ' ') {
                        return@InputFilter source.dropLast(1)
                    }
                }
                null
            }
        val lengthFilter = InputFilter.LengthFilter(MAX_TEXT_LENGTH)
        val bigOnlyFirstLetterFilter = InputFilter { source, start, end, _, _, _ ->
            var upperCase = 0
            for (i in start until end) {
                if (Character.isUpperCase(source[i]) && checkIsNotSpace(source[i]) && isNotFirstChar(
                        upperCase
                    )
                )
                    return@InputFilter source.dropLast(1)
                upperCase++
            }
            null
        }
        editText.filters = arrayOf(lengthFilter, regexFilter, bigOnlyFirstLetterFilter)
        editText.addTextChangedListener { text ->
            val splitNames = text.toString().split("\\s".toRegex())
            val spacesAmount = text.toString().count { it == ' ' }
            val surname = splitNames.getOrNull(1).orEmpty()
            if (validateName(splitNames.first(), splitNames, editText, context) &&
                validateSurname(surname, splitNames, spacesAmount, editText, context)
            )
                editText.error = null
        }
    }

    fun checkIsNotSpace(source: Char): Boolean {
        if (source.isWhitespace())
            return false
        return true
    }

    fun isNotFirstChar(upperCase: Int): Boolean {
        if (upperCase > 1)
            return true
        return false
    }


    fun checkListSizeIsTwo(splitNames: List<String>): Boolean {
        if (splitNames.size >= 2)
            return true
        return false
    }

    fun validateName(
        name: String, splitNames: List<String>, editText: EditText,
        context: Context
    ): Boolean {
        if (name.length < MIN_NAME_AND_SURNAME_LENGTH && splitNames.first().isNotEmpty()) {
            editText.error = context.getString(R.string.invalid_name_message)
            return false
        } else if (name.isEmpty()) {
            editText.error = context.getString(R.string.invalid_empty_message)
            return false
        } else if (!name.first().isUpperCase()) {
            editText.error = context.getString(R.string.invalid_name_message)
            return false
        }
        return true
    }

    fun validateSurname(
        surname: String, splitNames: List<String>, spacesAmount: Int,
        editText: EditText, context: Context
    ): Boolean {
        if (surname.length < MIN_NAME_AND_SURNAME_LENGTH && checkListSizeIsTwo(splitNames)
            && spacesAmount == MIN_SPACE_AMOUNT
        ) {
            editText.error = context.getString(R.string.invalid_surname_message)
            return false
        } else if (spacesAmount > MIN_SPACE_AMOUNT) {
            editText.error = context.getString(R.string.invalid_spaces_message)
            return false
        } else if (surname.isEmpty()) {
            editText.error = context.getString(R.string.invalid_surname_message)
            return false
        } else if (!surname[0].isUpperCase()) {
            editText.error = context.getString(R.string.invalid_surname_message)
            return false
        }
        return true
    }
}