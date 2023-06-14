package com.safetyheads.akademiaandroida.presentation.ui.fragments.changepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.presentation.ui.utils.PasswordValidator
import kotlinx.coroutines.launch

class ChangePasswordViewModel(private val changePasswordUseCase: ChangePasswordUseCase) : ViewModel() {

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    private val _isSaveEnable = MutableLiveData<Boolean>()
    val isSaveEnable: LiveData<Boolean> get() = _isSaveEnable

    private var currentPassword = ""
    private var newPassword = ""
    private var confirmNewPassword = ""
    fun changePassword(oldPassword: String, newPassword: String) {
        viewModelScope.launch {

            changePasswordUseCase.invoke(ChangePasswordUseCase.ChangeParam(oldPassword, newPassword)).collect { result ->
                _isSuccess.postValue(result.isSuccess)
            }
        }
    }

    fun currentPasswordChanged(password: String) {
        currentPassword = password
        validate()
    }

    fun newPasswordChanged(password: String) {
        newPassword = password
        validate()
    }

    fun confirmNewPasswordChanged(password: String) {
        confirmNewPassword = password
        validate()
    }

    private fun validate() {
        val currentPasswordValid = PasswordValidator.isValid(currentPassword)
        val newPasswordValid = PasswordValidator.isValid(newPassword)
        val confirmNewPasswordValid = PasswordValidator.isValid(confirmNewPassword)
        val newPasswordSame = newPassword == confirmNewPassword
        _isSaveEnable.postValue(currentPasswordValid && newPasswordValid && confirmNewPasswordValid && newPasswordSame)
    }
}