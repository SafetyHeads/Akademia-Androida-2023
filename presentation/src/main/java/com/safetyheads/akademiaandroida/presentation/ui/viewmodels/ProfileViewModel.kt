package com.safetyheads.akademiaandroida.presentation.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Profile
import com.safetyheads.akademiaandroida.domain.usecases.ChangeCityAddressUseCase
import com.safetyheads.akademiaandroida.domain.usecases.ChangeCountryAddressUseCase
import com.safetyheads.akademiaandroida.domain.usecases.ChangeJobPositionUseCase
import com.safetyheads.akademiaandroida.domain.usecases.ChangeNameUseCase
import com.safetyheads.akademiaandroida.domain.usecases.ChangePhoneNumberUseCase
import com.safetyheads.akademiaandroida.domain.usecases.ChangeStreetAddressUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetProfileInformationUseCase
import com.safetyheads.akademiaandroida.domain.usecases.LoginUseCase
import com.safetyheads.akademiaandroida.domain.usecases.ProfileDeleteAccountUseCase
import com.safetyheads.akademiaandroida.domain.usecases.ProfileLogOutUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val loginUseCase: LoginUseCase,
    private val getProfileInformationUseCase: GetProfileInformationUseCase,
    private val changeCityAddressUseCase: ChangeCityAddressUseCase,
    private val changeCountryAddressUseCase: ChangeCountryAddressUseCase,
    private val changeJobPositionUseCase: ChangeJobPositionUseCase,
    private val changeNameUseCase: ChangeNameUseCase,
    private val changePhoneNumberUseCase: ChangePhoneNumberUseCase,
    private val changeStreetAddressUseCase: ChangeStreetAddressUseCase,
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
            getProfileInformationUseCase.invoke(GetProfileInformationUseCase.ProfileParam(userUUID)).collect { profileInformation ->
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

    fun changeCityAddress(zipCode: String, city: String) {
        viewModelScope.launch {
            changeCityAddressUseCase.invoke(ChangeCityAddressUseCase.Param(zipCode, city, userUUID.value.orEmpty())).collect { cityAddressResult ->
                if (cityAddressResult.isSuccess) {
                    Log.i("ProfileViewModel", cityAddressResult.getOrNull().toString())
                } else {
                    Log.i("ProfileViewModel", cityAddressResult.exceptionOrNull()?.message.orEmpty())
                }
            }
        }
    }

    fun changeCountryAddress(country: String) {
        viewModelScope.launch {
            changeCountryAddressUseCase.invoke(ChangeCountryAddressUseCase.Param(country, userUUID.value.orEmpty())).collect { countryResult ->
                if (countryResult.isSuccess) {
                    Log.i("ProfileViewModel", countryResult.getOrNull().toString())
                } else {
                    Log.i("ProfileViewModel", countryResult.exceptionOrNull()?.message.orEmpty())
                }
            }
        }
    }

    fun changeStreetAddress(streetName: String, streetNumber: String) {
        viewModelScope.launch {
            changeStreetAddressUseCase.invoke(ChangeStreetAddressUseCase.Param(streetName, streetNumber, userUUID.value.orEmpty())).collect { streetAddressResult ->
                if (streetAddressResult.isSuccess) {
                    Log.i("ProfileViewModel", streetAddressResult.getOrNull().toString())
                } else {
                    Log.i("ProfileViewModel", streetAddressResult.exceptionOrNull()?.message.orEmpty())
                }
            }
        }
    }

    fun changePhoneNumber(phoneNumber: String) {
        viewModelScope.launch {
            changePhoneNumberUseCase.invoke(ChangePhoneNumberUseCase.Param(phoneNumber, userUUID.value.orEmpty())).collect { changePhoneResult ->
                if (changePhoneResult.isSuccess) {
                    Log.i("ProfileViewModel", changePhoneResult.getOrNull().toString())
                } else {
                    Log.i("ProfileViewModel", changePhoneResult.exceptionOrNull()?.message.orEmpty())
                }
            }
        }
    }

    fun changeJobPosition(jobPosition: String) {
        viewModelScope.launch {
            changeJobPositionUseCase.invoke(ChangeJobPositionUseCase.Param(jobPosition, userUUID.value.orEmpty())).collect { changeJobResult ->
                if (changeJobResult.isSuccess) {
                    Log.i("ProfileViewModel", changeJobResult.getOrNull().toString())
                } else {
                    Log.i("ProfileViewModel", changeJobResult.exceptionOrNull()?.message.orEmpty())
                }
            }
        }
    }

    fun changeName(firstName: String, lastName: String) {
        viewModelScope.launch {
            changeNameUseCase.invoke(ChangeNameUseCase.Param(firstName, lastName, userUUID.value.orEmpty())).collect { changeNameResult ->
                if (changeNameResult.isSuccess) {
                    Log.i("ProfileViewModel", changeNameResult.getOrNull().toString())
                } else {
                    Log.i("ProfileViewModel", changeNameResult.exceptionOrNull()?.message.orEmpty())
                }
            }
        }
    }

    fun deleteProfile() {
        viewModelScope.launch {
            profileDeleteAccountUseCase.invoke(ProfileDeleteAccountUseCase.Param()).collect { deleteProfileResult ->
                if (deleteProfileResult.isSuccess) {
                    deleteProfile.postValue(true)
                } else {
                    Log.i("ProfileViewModel", deleteProfileResult.exceptionOrNull()?.message.orEmpty())
                }
            }
        }
    }

    fun logOutProfile() {
        viewModelScope.launch {
            profileLogOutUseCase.invoke(ProfileLogOutUseCase.Param()).collect { logOutProfileResult ->
                if (logOutProfileResult.isSuccess) {
                    logOutProfile.postValue(true)
                } else {
                    Log.i("ProfileViewModel", logOutProfileResult.exceptionOrNull()?.message.orEmpty())
                }
            }
        }
    }

    fun resetProfileEntities() {
        logOutProfile.value = false
        deleteProfile.value = false
    }

}