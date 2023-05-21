package com.safetyheads.akademiaandroida.presentation.ui.fragments.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.domain.usecases.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _loginState = MutableLiveData<LoginState>(null)
    val loginState: LiveData<LoginState> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase.invoke(LoginUseCase.LoginParam(email, password)).collect { result ->

                if (result.isSuccess) {
                    _loginState.value = LoginState.SUCCESS
                } else {
                    _loginState.value = LoginState.ERROR
                }
            }
        }
    }
}

enum class LoginState {
    SUCCESS, ERROR, LOADING
}