package com.safetyheads.akademiaandroida.presentation.ui.viewmodels

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.data.network.repository.ImageRepositoryImpl
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Profile
import com.safetyheads.akademiaandroida.domain.usecases.AddImageToBitmapStorage
import com.safetyheads.akademiaandroida.domain.usecases.AddImageToUriStorage
import com.safetyheads.akademiaandroida.domain.usecases.AddImageToUserProfile
import com.safetyheads.akademiaandroida.domain.usecases.ChangeUserUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetProfileInformationUseCase
import com.safetyheads.akademiaandroida.domain.usecases.IsLoggedInUseCase
import com.safetyheads.akademiaandroida.domain.usecases.LoginUseCase
import com.safetyheads.akademiaandroida.domain.usecases.ProfileDeleteAccountUseCase
import com.safetyheads.akademiaandroida.domain.usecases.ProfileLogOutUseCase
import com.safetyheads.akademiaandroida.domain.usecases.RemoveImageFromStorage
import com.safetyheads.akademiaandroida.domain.usecases.RemoveImageFromUserProfile
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val loginUseCase: LoginUseCase,
    private val getProfileInformationUseCase: GetProfileInformationUseCase,
    private val addImageToUriStorage: AddImageToUriStorage,
    private val addImageToBitmapStorage: AddImageToBitmapStorage,
    private val addImageToUserProfile: AddImageToUserProfile,
    private val removeImageFromUserProfile: RemoveImageFromUserProfile,
    private val removeImageFromStorage: RemoveImageFromStorage,
    private val changeUserUseCase: ChangeUserUseCase,
    private val profileDeleteAccountUseCase: ProfileDeleteAccountUseCase,
    private val profileLogOutUseCase: ProfileLogOutUseCase,
    private val isLoggedInUseCase : IsLoggedInUseCase
) : ViewModel() {

    private val _doesExistUser: MutableLiveData<Boolean> = MutableLiveData()

    val userUUID: MutableLiveData<String> = MutableLiveData()
    val userInformation: MutableLiveData<Profile> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val errorMessage: MutableLiveData<Throwable> = MutableLiveData()
    val logOutProfile: MutableLiveData<Boolean> = MutableLiveData(false)
    val deleteProfile: MutableLiveData<Boolean> = MutableLiveData(false)
    val doesExistUser: LiveData<Boolean> = _doesExistUser

    fun getSessionInfo() {
        viewModelScope.launch {
            isLoggedInUseCase.invoke().collect { sessionInfo ->
                if (sessionInfo.isSuccess) {
                    _doesExistUser.postValue(sessionInfo.getOrNull())
                } else {
                    Log.i("DashboardViewModel", sessionInfo.exceptionOrNull()?.message.orEmpty())
                }

            }
        }
    }

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

    fun addImageToUriStorage(imageUri: Uri) {
        isLoading.postValue(true)
        viewModelScope.launch {
            addImageToUriStorage.invoke(
                AddImageToUriStorage.ImageParam(
                    ImageRepositoryImpl.AndroidUri(
                        imageUri
                    )
                )
            )
                .collect { imageUri ->
                    if (imageUri.isSuccess) {
                        addImageToUserProfile(imageUri.getOrNull().orEmpty())
                    } else {
                        isLoading.postValue(false)
                        Log.i("ProfileViewModel", imageUri.exceptionOrNull()?.message.orEmpty())
                    }
                }
        }
    }

    fun addImageToBitmapStorage(imageBitmap: Bitmap) {
        isLoading.postValue(true)
        viewModelScope.launch {
            addImageToBitmapStorage.invoke(
                AddImageToBitmapStorage.ImageParam(
                    ImageRepositoryImpl.AndroidBitmap(imageBitmap)
                )
            ).collect { imageUri ->
                if (imageUri.isSuccess) {
                    addImageToUserProfile(imageUri.getOrNull().orEmpty())
                } else {
                    isLoading.postValue(false)
                    Log.i("ProfileViewModel", imageUri.exceptionOrNull()?.message.orEmpty())
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

    fun addImageToUserProfile(imageStringReference: String) {
        viewModelScope.launch {
            if (imageStringReference.isNotEmpty()) {
                addImageToUserProfile.invoke(
                    AddImageToUserProfile.ImageParam(
                        userUUID.value.orEmpty(),
                        imageStringReference
                    )
                ).collect { addImageResult ->
                    if (addImageResult.isSuccess) {
                        removeImageFromStorage(addImageResult.getOrNull().orEmpty())
                        isLoading.postValue(false)
                    } else {
                        isLoading.postValue(false)
                        Log.i(
                            "ProfileViewModel",
                            addImageResult.exceptionOrNull()?.message.orEmpty()
                        )
                    }
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

    fun removeImageFromStorage(imageStringReference: String) {
        viewModelScope.launch {
            if (imageStringReference != "default_user" && imageStringReference.isNotEmpty()) {
                removeImageFromStorage.invoke(
                    RemoveImageFromStorage.ImageParam(
                        imageStringReference
                    )
                ).collect { removeImageResult ->
                    if (removeImageResult.isSuccess) {
                        Log.i(
                            this@ProfileViewModel.toString(),
                            "Remove image from Firebase Storage successful!"
                        )
                    } else {
                        Log.i(
                            this@ProfileViewModel.toString(),
                            "Remove image from Firebase Storage no successful!"
                        )
                    }
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

    fun removeImageFromUserProfile() {
        isLoading.postValue(true)
        viewModelScope.launch {
            removeImageFromUserProfile.invoke(
                RemoveImageFromUserProfile.ImageParam(
                    userUUID.value.orEmpty()
                )
            ).collect { removeImageResult ->
                if (removeImageResult.isSuccess) {
                    isLoading.postValue(false)
                    removeImageFromStorage(removeImageResult.getOrNull().orEmpty())
                } else {
                    isLoading.postValue(false)
                    Log.i(
                        "ProfileViewModel",
                        removeImageResult.exceptionOrNull()?.message.orEmpty()
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
                    Log.i(
                        "ProfileViewModel",
                        cityAddressResult.getOrNull().toString()
                    )
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
                    Log.i(
                        "ProfileViewModel",
                        cityAddressResult.getOrNull().toString()
                    )
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
                    Log.i(
                        "ProfileViewModel",
                        cityAddressResult.getOrNull().toString()
                    )
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
                    Log.i(
                        "ProfileViewModel",
                        cityAddressResult.getOrNull().toString()
                    )
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
            profileDeleteAccountUseCase.invoke(
                ProfileDeleteAccountUseCase.Param(
                    userUUID.value.orEmpty()
                )
            ).collect { deleteProfileResult ->
                if (deleteProfileResult.isSuccess) {
                    deleteProfile.postValue(true)
                    removeImageFromStorage(deleteProfileResult.getOrNull().orEmpty())
                    resetProfileEntities()
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
                        resetProfileEntities()
                    } else {
                        Log.i(
                            "ProfileViewModel",
                            logOutProfileResult.exceptionOrNull()?.message.orEmpty()
                        )
                    }
                }
        }
    }

    private fun resetProfileEntities() {
        logOutProfile.value = false
        deleteProfile.value = false
    }
}