package com.safetyheads.akademiaandroida.presentation.ui.sign_up


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.domain.usecases.RegisterUseCase
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    fun signUp(fullName: String, emailAddress: String, password: String) {

        @SuppressWarnings("UnusedPrivateMember")
        val createUser = viewModelScope.launch {
            registerUseCase.invoke(RegisterUseCase.UserParams(fullName, emailAddress, password))
        }
    }
}
