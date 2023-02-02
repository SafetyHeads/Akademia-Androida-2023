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

    private const val maxTextLength = 10
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
        editText.filters = arrayOf<InputFilter>(lengthFilter, regexFilter)
        editText.addTextChangedListener { text ->
            val splitNames = text.toString().split("\\s".toRegex())
            val spacesAmount = text.toString().count { it == ' ' }
            if (validateName(splitNames.first(), splitNames, editText, context) &&
                validateSurname(splitNames.last(), splitNames, spacesAmount, editText, context)
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
        }
        return true
    }
}