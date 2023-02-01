package com.safetyheads.akademiaandroida

import android.text.InputFilter
import android.text.Spanned
import android.widget.Toast


class PasswordValidator : InputFilter {
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val password = dest.toString() + source.toString()
        return if (password.matches(PASSWORD_REGEX.toRegex())) null else ""
    }

    companion object {
        private const val PASSWORD_REGEX = "\"^[a-zA-Z0-9]\\\\w{11,}\$\"$"
    }
}