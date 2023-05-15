package com.safetyheads.akademiaandroida.presentation.ui.utils

import android.widget.EditText

internal fun EditText.isCorrectOrEmptyText(): Boolean {
    return if (this.text.isNullOrEmpty()) {
        false
    } else {
        this.error.isNullOrEmpty()
    }
}