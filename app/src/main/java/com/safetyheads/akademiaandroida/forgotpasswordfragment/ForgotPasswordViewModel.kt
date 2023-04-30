package com.safetyheads.akademiaandroida.forgotpasswordfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.domain.usecases.ResetPasswordUseCase
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(private val resetPasswordUseCase: ResetPasswordUseCase) :
    ViewModel() {
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun setError(errorMsg: String?) {
        _error.value = errorMsg
    }

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    fun resetPassword(email: String) {
        viewModelScope.launch {

            resetPasswordUseCase.invoke(ResetPasswordUseCase.ResetParam(email)).collect { result ->
                _isSuccess.postValue(result.isSuccess)
            }
        }
    }
}
