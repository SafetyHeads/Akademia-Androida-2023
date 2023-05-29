package com.safetyheads.akademiaandroida.presentation.ui.fragments.changepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ChangePasswordViewModel(private val changePasswordUseCase: ChangePasswordUseCase) : ViewModel() {

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    fun changePassword(oldPassword: String, newPassword: String) {
        viewModelScope.launch {

            changePasswordUseCase.invoke(ChangePasswordUseCase.ChangeParam(oldPassword, newPassword)).collect { result ->
                _isSuccess.postValue(result.isSuccess)
            }
        }
    }
}