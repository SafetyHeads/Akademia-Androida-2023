package com.safetyheads.akademiaandroida.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.domain.usecases.RegisterUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel (
    private val registerUseCase: RegisterUseCase
    ) : ViewModel() {
    fun signUp(fullName: String, emailAddress: String, password: String, confirmedPassword: String) {

        val createUser = viewModelScope.launch {
            registerUseCase.invoke(RegisterUseCase.UserParams(fullName, emailAddress, password))
        }
    }
}