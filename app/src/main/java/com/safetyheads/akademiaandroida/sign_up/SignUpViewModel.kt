package com.safetyheads.akademiaandroida.sign_up

import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText
import javax.inject.Inject

class SignUpViewModel @Inject constructor (
    private val registerUseCase: RegisterUseCase
    ) : ViewModel() {
    fun signUp(fullName: String, emailAddress: String, password: String, confirmedPassword: String) {

    }
}