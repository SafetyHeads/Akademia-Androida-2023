package com.safetyheads.akademiaandroida.presentation.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Profile
import com.safetyheads.akademiaandroida.domain.usecases.ChangeUserUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetProfileInformationUseCase
import com.safetyheads.akademiaandroida.domain.usecases.LoginUseCase
import com.safetyheads.akademiaandroida.domain.usecases.ProfileDeleteAccountUseCase
import com.safetyheads.akademiaandroida.domain.usecases.ProfileLogOutUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val loginUseCase: LoginUseCase,
    private val getProfileInformationUseCase: GetProfileInformationUseCase,
    private val changeUserUseCase: ChangeUserUseCase,
    private val profileDeleteAccountUseCase: ProfileDeleteAccountUseCase,
    private val profileLogOutUseCase: ProfileLogOutUseCase
) : ViewModel() {

    val userUUID: MutableLiveData<String> = MutableLiveData()
    val userInformation: MutableLiveData<Profile> = MutableLiveData()
    val logOutProfile: MutableLiveData<Boolean> = MutableLiveData(false)
    val deleteProfile: MutableLiveData<Boolean> = MutableLiveData(false)

    fun tempLogin() {
        viewModelScope.launch {
            loginUseCase.invoke(LoginUseCase.LoginParam("", "")).collect { userUUIDResult ->
                if (userUUIDResult.isSuccess) {
                    userUUID.value = userUUIDResult.getOrNull()
                    getProfileInformation(userUUID.value.orEmpty())
                } else {
                    Log.i("ProfileViewModel", userUUIDResult.exceptionOrNull()?.message.orEmpty())
                }
            }
        }
    }

    fun getProfileInformation(userUUID: String) {
        viewModelScope.launch {
            getProfileInformationUseCase.invoke(GetProfileInformationUseCase.ProfileParam(userUUID))
                .collect { profileInformation ->
                    if (profileInformation.isSuccess) {
                        val tempUserInformation = profileInformation.getOrNull()
                        if (tempUserInformation != null)
                            userInformation.postValue(tempUserInformation.copy())
                    } else {
                        Log.i(
                            "ProfileViewModel",
                            profileInformation.exceptionOrNull()?.message.orEmpty()
                        )
                    }
                }
        }
    }

    fun changeCityAddress(city: String) {
        viewModelScope.launch {
            val mapChange: Map<String, Any> = mapOf(
                "address.city" to city
            )

            changeUserUseCase.invoke(
                ChangeUserUseCase.Param(
                    mapChange,
                    "changeCityAddress",
                    userUUID.value.orEmpty()
                )
            ).collect { cityAddressResult ->
                if (cityAddressResult.isSuccess) {
                    Log.i("ProfileViewModel", cityAddressResult.getOrNull().toString())
                } else {
                    Log.i(
                        "ProfileViewModel",
                        cityAddressResult.exceptionOrNull()?.message.orEmpty()
                    )
                }
            }
        }
    }

    fun changeCountryAddress(country: String) {
        viewModelScope.launch {
            val mapChange: Map<String, Any> = mapOf(
                "address.country" to country
            )

            changeUserUseCase.invoke(
                ChangeUserUseCase.Param(
                    mapChange,
                    "changeCountryAddress",
                    userUUID.value.orEmpty()
                )
            ).collect { cityAddressResult ->
                if (cityAddressResult.isSuccess) {
                    Log.i("ProfileViewModel", cityAddressResult.getOrNull().toString())
                } else {
                    Log.i(
                        "ProfileViewModel",
                        cityAddressResult.exceptionOrNull()?.message.orEmpty()
                    )
                }
            }
        }
    }

    fun changeStreetAddress(streetName: String) {
        viewModelScope.launch {
            val mapChange: Map<String, Any> = mapOf(
                "address.streetName" to streetName
            )

            changeUserUseCase.invoke(
                ChangeUserUseCase.Param(
                    mapChange,
                    "changeStreetAddress",
                    userUUID.value.orEmpty()
                )
            ).collect { cityAddressResult ->
                if (cityAddressResult.isSuccess) {
                    Log.i("ProfileViewModel", cityAddressResult.getOrNull().toString())
                } else {
                    Log.i(
                        "ProfileViewModel",
                        cityAddressResult.exceptionOrNull()?.message.orEmpty()
                    )
                }
            }
        }
    }

    fun changePhoneNumber(phoneNumber: String) {
        viewModelScope.launch {
            val mapChange: Map<String, Any> = mapOf(
                "phoneNumber" to phoneNumber
            )

            changeUserUseCase.invoke(
                ChangeUserUseCase.Param(
                    mapChange,
                    "changePhoneNumber",
                    userUUID.value.orEmpty()
                )
            ).collect { cityAddressResult ->
                if (cityAddressResult.isSuccess) {
                    Log.i("ProfileViewModel", cityAddressResult.getOrNull().toString())
                } else {
                    Log.i(
                        "ProfileViewModel",
                        cityAddressResult.exceptionOrNull()?.message.orEmpty()
                    )
                }
            }
        }
    }

    fun changeStreetNumber(streetNumber: String) {
        viewModelScope.launch {
            val mapChange: Map<String, Any> = mapOf(
                "address.streetNumber" to streetNumber,
            )

            changeUserUseCase.invoke(
                ChangeUserUseCase.Param(
                    mapChange,
                    "changeStreetNumber",
                    userUUID.value.orEmpty()
                )
            ).collect { cityAddressResult ->
                if (cityAddressResult.isSuccess) {
                    Log.i("ProfileViewModel", cityAddressResult.getOrNull().toString())
                } else {
                    Log.i(
                        "ProfileViewModel",
                        cityAddressResult.exceptionOrNull()?.message.orEmpty()
                    )
                }
            }
        }
    }

    fun changeZipCode(zipCode: String) {
        viewModelScope.launch {
            val mapChange: Map<String, Any> = mapOf(
                "address.zipCode" to zipCode,
            )

            changeUserUseCase.invoke(
                ChangeUserUseCase.Param(
                    mapChange,
                    "changeZipCodeNumber",
                    userUUID.value.orEmpty()
                )
            ).collect { cityAddressResult ->
                if (cityAddressResult.isSuccess) {
                    Log.i("ProfileViewModel", cityAddressResult.getOrNull().toString())
                } else {
                    Log.i(
                        "ProfileViewModel",
                        cityAddressResult.exceptionOrNull()?.message.orEmpty()
                    )
                }
            }
        }
    }

    fun changeJobPosition(jobPosition: String) {
        viewModelScope.launch {
            val mapChange: Map<String, Any> = mapOf(
                "jobPosition" to jobPosition
            )

            changeUserUseCase.invoke(
                ChangeUserUseCase.Param(
                    mapChange,
                    "changeJobPosition",
                    userUUID.value.orEmpty()
                )
            ).collect { cityAddressResult ->
                if (cityAddressResult.isSuccess) {
                    Log.i("ProfileViewModel", cityAddressResult.getOrNull().toString())
                } else {
                    Log.i(
                        "ProfileViewModel",
                        cityAddressResult.exceptionOrNull()?.message.orEmpty()
                    )
                }
            }
        }
    }

    fun changeName(firstName: String, lastName: String) {
        viewModelScope.launch {
            val mapChange: Map<String, Any> = mapOf(
                "firstName" to firstName,
                "lastName" to lastName
            )

            changeUserUseCase.invoke(
                ChangeUserUseCase.Param(
                    mapChange,
                    "changeName",
                    userUUID.value.orEmpty()
                )
            ).collect { cityAddressResult ->
                if (cityAddressResult.isSuccess) {
                    Log.i("ProfileViewModel", cityAddressResult.getOrNull().toString())
                } else {
                    Log.i(
                        "ProfileViewModel",
                        cityAddressResult.exceptionOrNull()?.message.orEmpty()
                    )
                }
            }
        }
    }

    fun deleteProfile() {
        viewModelScope.launch {
            profileDeleteAccountUseCase.invoke(ProfileDeleteAccountUseCase.Param())
                .collect { deleteProfileResult ->
                    if (deleteProfileResult.isSuccess) {
                        deleteProfile.postValue(true)
                    } else {
                        Log.i(
                            "ProfileViewModel",
                            deleteProfileResult.exceptionOrNull()?.message.orEmpty()
                        )
                    }
                }
        }
    }

    fun logOutProfile() {
        viewModelScope.launch {
            profileLogOutUseCase.invoke(ProfileLogOutUseCase.Param())
                .collect { logOutProfileResult ->
                    if (logOutProfileResult.isSuccess) {
                        logOutProfile.postValue(true)
                    } else {
                        Log.i(
                            "ProfileViewModel",
                            logOutProfileResult.exceptionOrNull()?.message.orEmpty()
                        )
                    }
                }
        }
    }

    fun resetProfileEntities() {
        logOutProfile.value = false
        deleteProfile.value = false
    }

}