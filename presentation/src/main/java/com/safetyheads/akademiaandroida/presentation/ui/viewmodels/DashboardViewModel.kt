package com.safetyheads.akademiaandroida.presentation.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.domain.usecases.GetSessionUseCase
import kotlinx.coroutines.launch

class DashboardViewModel(private val getSessionUseCase: GetSessionUseCase) : ViewModel() {
    private val _isExistUser : MutableLiveData<Boolean> = MutableLiveData()

    val isExistUser: LiveData<Boolean> = _isExistUser

    fun getSessionInfo() {
        viewModelScope.launch {
            getSessionUseCase.invoke().collect {sessionInfo ->
                if(sessionInfo.isSuccess) {
                    _isExistUser.postValue(sessionInfo.getOrNull())
                } else {
                    Log.i("DashboardViewModel", sessionInfo.exceptionOrNull()?.message.orEmpty())
                }

            }
        }
    }
}