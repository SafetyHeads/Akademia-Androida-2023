package com.safetyheads.akademiaandroida.presentation.ui.utils

import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager

object ViewExt {
    fun View.hideKeyboard(){
        val imm=context.getSystemService(INPUT_METHOD_SERVICE)
        if (imm is InputMethodManager){
            imm.hideSoftInputFromWindow(windowToken,0)
        }
    }
}