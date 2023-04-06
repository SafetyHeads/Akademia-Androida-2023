package com.safetyheads.akademiaandroida.sign_up

import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText
import com.safetyheads.akademiaandroida.dropdownlist.LoadItemsToDropDownListUseCase
import kotlinx.coroutines.flow.MutableStateFlow

class SignUpViewModel (private val signUpUseCase: SignUpUseCase) : ViewModel() {
    fun signUp(eTextFullName: TextInputEditText, eTextEmailAddress: TextInputEditText, eTextPassword: TextInputEditText, eTextConfirmPassword: TextInputEditText) {

    }

}