package com.safetyheads.akademiaandroida.forgotpasswordfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ForgotPasswordViewModel(private val forgotPasswordUseCase: ForgotPasswordUseCase) : ViewModel() {
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun setError(errorMsg: String?) {
        _error.value = errorMsg
    }


}