package com.safetyheads.akademiaandroida.presentation.ui.viewmodels

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Image
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Profile
import com.safetyheads.akademiaandroida.domain.usecases.AddImageToFirebaseBitmapStorage
import com.safetyheads.akademiaandroida.domain.usecases.AddImageToFirebaseUriStorage
import com.safetyheads.akademiaandroida.domain.usecases.AddImageToFirebaseUserProfileFirestore
import com.safetyheads.akademiaandroida.domain.usecases.AddImageToFirestoreStorage
import com.safetyheads.akademiaandroida.domain.usecases.GetProfileInformationUseCase
import com.safetyheads.akademiaandroida.domain.usecases.LoginUseCase
import com.safetyheads.akademiaandroida.domain.usecases.RemoveImageToFirestoreFirebaseStorage
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val loginUseCase: LoginUseCase,
    private val getProfileInformationUseCase: GetProfileInformationUseCase,
    private val addImageToFirebaseUriStorage: AddImageToFirebaseUriStorage,
    private val addImageToFirebaseBitmapStorage: AddImageToFirebaseBitmapStorage,
    private val addImageToFirestoreStorage: AddImageToFirestoreStorage,
    private val addImageToFirebaseUserProfileFirestore: AddImageToFirebaseUserProfileFirestore,
    private val removeImageToFirestoreFirebaseStorage: RemoveImageToFirestoreFirebaseStorage
) : ViewModel() {

    val userUUID: MutableLiveData<String> = MutableLiveData()
    val userInformation: MutableLiveData<Profile> = MutableLiveData()

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
        viewModelScope.launch {
            addImageToFirebaseUriStorage.invoke(AddImageToFirebaseUriStorage.ProfileParam(imageUri)).collect { imageUri ->
                if (imageUri.isSuccess) {
                    addImageToFirestoreStorage(imageUri.getOrNull() ?: Image(Uri.EMPTY,""))
                } else {
                    val e = 5
                }
            }
        }
    }

    fun addImageToFirebaseBitmapStorage(imageBitmap: Bitmap) {
        viewModelScope.launch {
            addImageToFirebaseBitmapStorage.invoke(AddImageToFirebaseBitmapStorage.ProfileParam(imageBitmap)).collect { imageUri ->
                if (imageUri.isSuccess) {
                    addImageToFirestoreStorage(imageUri.getOrNull() ?: Image(Uri.EMPTY,""))
                } else {
                    val e = 5
                }
            }
        }
    }

    fun addImageToFirestoreStorage(image: Image) {
        viewModelScope.launch {
            addImageToFirestoreStorage.invoke(AddImageToFirestoreStorage.ProfileParam(image)).collect {
                if (it.isSuccess) {
                    it.getOrNull()?.let { it1 -> addImageToFirebaseUserProfile(it1) }
                } else {

                }
            }
        }
    }

    fun addImageToFirebaseUserProfile(imageStringReference: String) {
        viewModelScope.launch {
            addImageToFirebaseUserProfileFirestore.invoke(AddImageToFirebaseUserProfileFirestore.ProfileParam(userUUID.value.orEmpty(), imageStringReference)).collect { result ->
                if (result.isSuccess) {
                    val s = 0
                } else {

                }
            }
        }
    }

    fun removeImage() {
        viewModelScope.launch {
            removeImageToFirestoreFirebaseStorage.invoke(RemoveImageToFirestoreFirebaseStorage.ProfileParam(userUUID.value.orEmpty())).collect {
                if (it.isSuccess) {
                    val s = 0
                } else {
                    val k = 5
                }
            }
        }
    }



}