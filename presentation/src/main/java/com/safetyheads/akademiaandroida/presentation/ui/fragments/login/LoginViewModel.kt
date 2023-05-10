package com.safetyheads.akademiaandroida.presentation.ui.fragments.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.domain.entities.User
import com.safetyheads.akademiaandroida.domain.usecases.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _loginStatus = MutableStateFlow<Result<User>?>(null)
    val LoginState: StateFlow<Result<User>?> = _loginStatus
    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase.invoke(LoginUseCase.UserParams(email, password)).collect { result ->
                _loginStatus.value = result
            }
        }
    }
}