package com.safetyheads.akademiaandroida.presentation.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.domain.usecases.GetSessionUseCase
import kotlinx.coroutines.launch

class DashboardViewModel(private val getSessionUseCase: GetSessionUseCase) : ViewModel() {
    val isExistUser : MutableLiveData<Boolean> = MutableLiveData()

    fun getSessionInfo() {
        viewModelScope.launch {
            getSessionUseCase.invoke().collect {sessionInfo ->
                if(sessionInfo.isSuccess) {
                    isExistUser.postValue(sessionInfo.getOrNull() != null)
                } else {
                    Log.i("DashboardViewModel", sessionInfo.exceptionOrNull()?.message.orEmpty())
                }

            }
        }
    }
}