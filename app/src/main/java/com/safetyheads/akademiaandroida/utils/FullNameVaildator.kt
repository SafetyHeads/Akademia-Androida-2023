package com.safetyheads.akademiaandroida.utils

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

    private const val maxTextLength = 100
    private const val minNameAndSurnameLength = 2
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
        val lengthFilter = InputFilter.LengthFilter(maxTextLength)
        val bigOnlyFirstLetterFilter = InputFilter { source, start, end, _, _, _ ->
            var upperCase = 0
            for (i in start until end) {
                if (Character.isUpperCase(source[i]) && source[i] != ' ' && upperCase > 1)
                    return@InputFilter source.dropLast(1)
                upperCase++
            }
            null
        }
        editText.filters = arrayOf(lengthFilter, regexFilter, bigOnlyFirstLetterFilter)
        editText.addTextChangedListener { text ->
            val splitNames = text.toString().split("\\s".toRegex())
            val spacesAmount = text.toString().count { it == ' ' }
            val surname = if (splitNames.size > 1) splitNames[1] else ""
            if (validateName(splitNames.first(), splitNames, editText, context) &&
                validateSurname(surname, splitNames, spacesAmount, editText, context)
            )
                editText.error = null
        }
    }

    private fun validateName(
        name: String, splitNames: List<String>, editText: EditText,
        context: Context
    ): Boolean {
        if (name.length < minNameAndSurnameLength && splitNames.first().isNotEmpty()) {
            editText.error = context.getString(R.string.invalid_name_message)
            return false
        } else if (name.isEmpty()) {
            editText.error = context.getString(R.string.invalid_empty_message)
            return false
        } else if (!name[0].isUpperCase()) {
            editText.error = context.getString(R.string.invalid_name_message)
            return false
        }
        return true
    }

    private fun validateSurname(
        surname: String, splitNames: List<String>, spacesAmount: Int,
        editText: EditText, context: Context
    ): Boolean {
        if (surname.length < minNameAndSurnameLength && splitNames.size >= 2 && spacesAmount == 1) {
            editText.error = context.getString(R.string.invalid_surname_message)
            return false
        } else if (spacesAmount > 1) {
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