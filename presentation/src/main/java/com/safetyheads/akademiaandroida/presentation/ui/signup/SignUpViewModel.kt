package com.safetyheads.akademiaandroida.presentation.ui.signup


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.domain.entities.User
import com.safetyheads.akademiaandroida.domain.usecases.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _registrationState = MutableStateFlow<Result<User>?>(null)
    val registrationState: StateFlow<Result<User>?> = _registrationState

    fun signUp(fullName: String, emailAddress: String, password: String) {

        viewModelScope.launch {
            registerUseCase.invoke(RegisterUseCase.UserParams(fullName, emailAddress, password))
                .collect { result ->
                    _registrationState.value = result
                }
        }
    }
}
