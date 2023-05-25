package com.safetyheads.akademiaandroida.presentation.ui.viewmodels

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.data.network.repository.ImageRepositoryImpl
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Profile
import com.safetyheads.akademiaandroida.domain.usecases.AddImageToBitmapStorage
import com.safetyheads.akademiaandroida.domain.usecases.AddImageToUriStorage
import com.safetyheads.akademiaandroida.domain.usecases.AddImageToUserProfile
import com.safetyheads.akademiaandroida.domain.usecases.GetProfileInformationUseCase
import com.safetyheads.akademiaandroida.domain.usecases.LoginUseCase
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
    private val removeImageFromStorage: RemoveImageFromStorage
) : ViewModel() {

    val userUUID: MutableLiveData<String> = MutableLiveData()
    val userInformation: MutableLiveData<Profile> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val errorMessage: MutableLiveData<Throwable> = MutableLiveData()

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
            addImageToUriStorage.invoke(AddImageToUriStorage.ImageParam(ImageRepositoryImpl.AndroidUri(imageUri)))
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

}