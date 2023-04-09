package com.safetyheads.akademiaandroida.forgotpasswordfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.domain.usecases.ResetPasswordUseCase
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(private val resetPasswordUseCase: ResetPasswordUseCase) :
    ViewModel() {
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun setError(errorMsg: String?) {
        _error.value = errorMsg
    }

    fun resetPassword(email: String) {
        //TODO: change on network result
        viewModelScope.launch {
            resetPasswordUseCase.invoke(ResetPasswordUseCase.ResetParam(email))
        }

    }


}