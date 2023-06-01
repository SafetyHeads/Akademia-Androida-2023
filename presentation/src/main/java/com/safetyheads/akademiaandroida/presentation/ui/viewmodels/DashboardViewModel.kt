package com.safetyheads.akademiaandroida.presentation.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.domain.usecases.IsLoggedInUseCase
import com.safetyheads.akademiaandroida.domain.usecases.ProfileLogOutUseCase
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val logOutUseCase: ProfileLogOutUseCase
) : ViewModel() {
    private val _isExistUser: MutableLiveData<Boolean> = MutableLiveData()
    private val _islogOut: MutableLiveData<Boolean> = MutableLiveData()

    val isExistUser: LiveData<Boolean> = _isExistUser
    val isLogOut : LiveData<Boolean> = _islogOut

    fun getSessionInfo() {
        viewModelScope.launch {
            isLoggedInUseCase.invoke().collect { sessionInfo ->
                if (sessionInfo.isSuccess) {
                    _isExistUser.postValue(sessionInfo.getOrNull())
                } else {
                    Log.i("DashboardViewModel", sessionInfo.exceptionOrNull()?.message.orEmpty())
                }

            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            logOutUseCase.invoke(ProfileLogOutUseCase.Param()).collect { isLogOutResult ->
                if(isLogOutResult.isSuccess) {
                    _islogOut.postValue(isLogOutResult.getOrNull())
                } else {
                    Log.i("DashboardViewModel", isLogOutResult.exceptionOrNull()?.message.orEmpty())
                }
            }
        }
    }
}