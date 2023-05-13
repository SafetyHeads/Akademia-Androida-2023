package com.safetyheads.akademiaandroida.presentation.ui.viewmodels

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.domain.entities.Playlist
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Profile
import com.safetyheads.akademiaandroida.domain.usecases.AddImageToFirebaseBitmapStorage
import com.safetyheads.akademiaandroida.domain.usecases.AddImageToFirebaseUriStorage
import com.safetyheads.akademiaandroida.domain.usecases.AddImageToFirebaseUserProfileFirestore
import com.safetyheads.akademiaandroida.domain.usecases.GetProfileInformationUseCase
import com.safetyheads.akademiaandroida.domain.usecases.LoginUseCase
import com.safetyheads.akademiaandroida.domain.usecases.RemoveImageFromFirebaseStorage
import com.safetyheads.akademiaandroida.domain.usecases.RemoveImageFromUserProfileFirestore
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val loginUseCase: LoginUseCase,
    private val getProfileInformationUseCase: GetProfileInformationUseCase,
    private val addImageToFirebaseUriStorage: AddImageToFirebaseUriStorage,
    private val addImageToFirebaseBitmapStorage: AddImageToFirebaseBitmapStorage,
    private val addImageToFirebaseUserProfileFirestore: AddImageToFirebaseUserProfileFirestore,
    private val removeImageFromUserProfileFirestore: RemoveImageFromUserProfileFirestore,
    private val removeImageFromFirebaseStorage: RemoveImageFromFirebaseStorage
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

    fun addImageToFirebaseUriStorage(imageUri: Uri) {
        isLoading.postValue(true)
        viewModelScope.launch {
            addImageToFirebaseUriStorage.invoke(AddImageToFirebaseUriStorage.ImageParam(imageUri)).collect { imageUri ->
                if (imageUri.isSuccess) {
                    addImageToFirebaseUserProfile(imageUri.getOrNull().orEmpty())
                } else {
                    isLoading.postValue(false)
                    Log.i("ProfileViewModel", imageUri.exceptionOrNull()?.message.orEmpty())
                }
            }
        }
    }

    fun addImageToFirebaseBitmapStorage(imageBitmap: Bitmap) {
        isLoading.postValue(true)
        viewModelScope.launch {
            addImageToFirebaseBitmapStorage.invoke(AddImageToFirebaseBitmapStorage.ImageParam(imageBitmap)).collect { imageUri ->
                if (imageUri.isSuccess) {
                    addImageToFirebaseUserProfile(imageUri.getOrNull().orEmpty())
                } else {
                    isLoading.postValue(false)
                    Log.i("ProfileViewModel", imageUri.exceptionOrNull()?.message.orEmpty())
                }
            }
        }
    }

    fun addImageToFirebaseUserProfile(imageStringReference: String) {
        viewModelScope.launch {
            if (imageStringReference.isNotEmpty()) {
                addImageToFirebaseUserProfileFirestore.invoke(AddImageToFirebaseUserProfileFirestore.ImageParam(userUUID.value.orEmpty(), imageStringReference)).collect { addImageResult ->
                    if (addImageResult.isSuccess) {
                        removeImageFromFirebaseStorage(addImageResult.getOrNull().orEmpty())
                        isLoading.postValue(false)
                    } else {
                        isLoading.postValue(false)
                        Log.i("ProfileViewModel", addImageResult.exceptionOrNull()?.message.orEmpty())
                    }
                }
            }
        }
    }

    fun removeImageFromFirebaseStorage(imageStringReference: String) {
        viewModelScope.launch {
            if (imageStringReference != "default_user" && imageStringReference.isNotEmpty()) {
                removeImageFromFirebaseStorage.invoke(RemoveImageFromFirebaseStorage.ImageParam(imageStringReference)).collect { removeImageResult ->
                    if (removeImageResult.isSuccess) {
                        Log.i(this@ProfileViewModel.toString(), "Remove image from Firebase Storage successful!")
                    } else {
                        Log.i(this@ProfileViewModel.toString(), "Remove image from Firebase Storage no successful!")
                    }
                }
            }
        }
    }

    fun removeImageFromUserProfile() {
        isLoading.postValue(true)
        viewModelScope.launch {
            removeImageFromUserProfileFirestore.invoke(RemoveImageFromUserProfileFirestore.ImageParam(userUUID.value.orEmpty())).collect { removeImageResult ->
                if (removeImageResult.isSuccess) {
                    isLoading.postValue(false)
                    removeImageFromFirebaseStorage(removeImageResult.getOrNull().orEmpty())
                } else {
                    isLoading.postValue(false)
                    Log.i("ProfileViewModel", removeImageResult.exceptionOrNull()?.message.orEmpty())
                }
            }
        }
    }

}