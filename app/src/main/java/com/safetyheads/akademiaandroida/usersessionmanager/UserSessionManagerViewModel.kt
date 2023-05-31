package com.safetyheads.akademiaandroida.usersessionmanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.domain.repositories.UserSessionManager
import com.safetyheads.akademiaandroida.domain.usecases.LoginUseCase
import com.safetyheads.akademiaandroida.domain.usecases.ProfileLogOutUseCase
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.getKoin

class UserSessionManagerViewModel(
    private val logOutUseCase: ProfileLogOutUseCase,
    private val logInUseCase: LoginUseCase
) : ViewModel() {
    private val exampleEmail = "dn311@gm.pl"
    private val examplePassword = "test12345678"

    private val _isLoggedIn : MutableLiveData<Boolean> = MutableLiveData()
    val isLoggedIn : LiveData<Boolean> = _isLoggedIn

    fun deleteSession() {
        viewModelScope.launch {
            logOutUseCase.invoke(ProfileLogOutUseCase.Param()).collect {
                if(it.isSuccess) {
                    checkSession()
                }
            }
        }
        checkSession()
    }

     fun createSession() {
        viewModelScope.launch {
            logInUseCase.invoke(LoginUseCase.LoginParam(email = exampleEmail, password = examplePassword)).collect {result ->
                if(result.isSuccess) {
                    checkSession()
                }
            }
        }
    }

    fun checkSession() {
        val sessionManager : UserSessionManager = getKoin().getSessionScope().get()
        _isLoggedIn.postValue(sessionManager.isLoggedIn)
    }
}