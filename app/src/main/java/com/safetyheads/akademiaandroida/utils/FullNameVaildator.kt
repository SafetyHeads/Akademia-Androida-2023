package com.safetyheads.akademiaandroida.utils

import android.content.Context
import android.text.InputFilter
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.safetyheads.akademiaandroida.R


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

        editText.addTextChangedListener {

            validateFullName(it.toString(), editText, context)

        }
    }

    private fun validateFullName(fullName: String, editText: EditText, context: Context) {
        val splitNames = fullName.split("\\s".toRegex())

        val spacesAmount = fullName.count { it == ' ' }

        // name
        if (splitNames.first().length < minNameAndSurnameLength && splitNames.first()
                .isNotEmpty()
        ) {
            editText.error = context.getString(R.string.invalid_name_message)
            return

        }
        //surname
        else if (splitNames.last().length < minNameAndSurnameLength && splitNames.size >= 2 && spacesAmount == 1) {
            editText.error = context.getString(R.string.invalid_surname_message)
            return

        } else if (fullName.isEmpty()) {
            editText.error = context.getString(R.string.invalid_empty_message)
            return

        } else if (spacesAmount > 1) {
            editText.error = context.getString(R.string.invalid_spaces_message)
        } else {
            editText.error = null
            return

        }
    }


}