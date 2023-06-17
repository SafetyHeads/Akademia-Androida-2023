package com.safetyheads.akademiaandroida.presentation.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.domain.usecases.AnonymousLoginUseCase
import kotlinx.coroutines.launch

class LaunchScreenViewModel(
    private val anonymousLoginUseCase: AnonymousLoginUseCase,
) : ViewModel() {

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val errorMessage: MutableLiveData<Throwable> = MutableLiveData()
    val loginResult: MutableLiveData<Boolean> = MutableLiveData(false)

    fun anonymousLogin() {
        isLoading.postValue(true)
        viewModelScope.launch {
            anonymousLoginUseCase.invoke().collect { anonymousLoginResult ->
                isLoading.postValue(false)
                if (anonymousLoginResult.isSuccess) {
                    loginResult.postValue(true)
                } else {
                    errorMessage.postValue(anonymousLoginResult.exceptionOrNull())
                }
            }
        }
    }
}