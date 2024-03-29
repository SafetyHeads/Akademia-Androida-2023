package com.safetyheads.akademiaandroida.presentation.ui.fragments.forgotpasswordfragment.usertest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.domain.usecases.GetMessagingTokenUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetProfileInformationUseCase
import com.safetyheads.akademiaandroida.domain.usecases.LoginUseCase
import com.safetyheads.akademiaandroida.domain.usecases.UpdateProfileFcmUseCase
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class UserTestViewModel(
    private val loginUseCase: LoginUseCase,
    private val userGetProfileInformationUseCase: GetProfileInformationUseCase,
    private val updateProfileFcmUseCase: UpdateProfileFcmUseCase,
    private val getMessagingTokenUseCase: GetMessagingTokenUseCase
) : ViewModel() {

    private var userUUID: String? = null
    private val _message: MutableLiveData<String> = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun login() {
        viewModelScope.launch {
            //Provide email and password
            loginUseCase.invoke(LoginUseCase.LoginParam("", "")).collect { userUUIDResult ->
                if (userUUIDResult.isSuccess) {
                    userUUID = userUUIDResult.getOrNull()
                    _message.postValue("Logged in")
                }
            }
        }
    }

    fun firebaseIdClicked() {
        viewModelScope.launch {
            getMessagingTokenUseCase.invoke()
                .collect { _message.postValue("Local token: $it") }
        }
    }

    fun showUser() {
        userUUID?.let { id ->
            viewModelScope.launch {
                userGetProfileInformationUseCase.invoke(GetProfileInformationUseCase.ProfileParam(id))
                    .collect { user ->
                        val storedToken = user.getOrNull()?.fcmToken
                        _message.postValue("Firestore stored token $storedToken")
                    }
            }
        } ?: _message.postValue("Not logged in")
    }

    fun setFcm() {
        userUUID?.let { id ->
            viewModelScope.launch {
                getMessagingTokenUseCase.invoke()
                    .map { result -> result.getOrNull() }
                    .flatMapConcat {
                        updateProfileFcmUseCase.invoke(UpdateProfileFcmUseCase.UpdateProfileParam(id, it.orEmpty()))
                    }
                    .collect {
                        _message.postValue("Token updated")
                    }
            }
        } ?: _message.postValue("Not logged in")
    }

    fun clearFcm() {
        userUUID?.let { id ->
            viewModelScope.launch {
                updateProfileFcmUseCase.invoke(UpdateProfileFcmUseCase.UpdateProfileParam(id, ""))
                    .collect {
                        _message.postValue("Token cleared")
                    }
            }
        } ?: _message.postValue("Not logged in")
    }
}

