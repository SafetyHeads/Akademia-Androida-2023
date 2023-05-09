package com.safetyheads.akademiaandroida.presentation.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Profile
import com.safetyheads.akademiaandroida.domain.usecases.GetProfileInformationUseCase
import com.safetyheads.akademiaandroida.domain.usecases.LoginUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val loginUseCase: LoginUseCase,
    private val getProfileInformationUseCase: GetProfileInformationUseCase
) : ViewModel() {

    val userUUID: MutableLiveData<String> = MutableLiveData()
    val userInformation: MutableLiveData<Profile> = MutableLiveData()

    fun tempLogin() {
        viewModelScope.launch {
            loginUseCase.invoke(LoginUseCase.LoginParam("", "")).collect { UUID ->
                if (UUID.isSuccess) {
                    userUUID.value = UUID.getOrNull()
                    getProfileInformation(userUUID.value.orEmpty())
                } else {
                    Log.i("ProfileViewModel", UUID.exceptionOrNull()?.message.orEmpty())
                }
            }
        }
    }

    fun getProfileInformation(UUID: String) {
        viewModelScope.launch {
            getProfileInformationUseCase.invoke(GetProfileInformationUseCase.ProfileParam(UUID)).collect { profileInformation ->
                if (profileInformation.isSuccess) {
                    val tempUserInformation = profileInformation.getOrNull()
                    if (tempUserInformation != null)
                        userInformation.postValue(tempUserInformation.copy())
                } else {
                    Log.i("ProfileViewModel", profileInformation.exceptionOrNull()?.message.orEmpty())
                }
            }
        }
    }
}