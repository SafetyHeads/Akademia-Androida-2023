package com.safetyheads.akademiaandroida.presentation.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.domain.entities.Media
import com.safetyheads.akademiaandroida.domain.usecases.GetAllVideoUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetInstagramImageUseCase
import kotlinx.coroutines.launch

class MediaViewModel(
    private val getInstagramImageUseCase: GetInstagramImageUseCase,
    private val getAllVideoUseCase: GetAllVideoUseCase
) : ViewModel() {

    init {
        getAllVideosFromYouTube()
        getInstagramImage()
    }

    val youtubeFilmList: MutableLiveData<List<Media>> = MutableLiveData()
    val instagramImageList: MutableLiveData<List<Media>> = MutableLiveData()
    val connectedList: MutableLiveData<List<Media>> = MutableLiveData()

    fun getInstagramImage() {
        viewModelScope.launch {
            getInstagramImageUseCase.invoke().collect() { imageList ->
                val tempImageResult = imageList.getOrNull()
                if (tempImageResult != null) {
                    instagramImageList.value = tempImageResult.orEmpty()
                    instagramImageList.postValue(tempImageResult.orEmpty())
                    getConnectedList()
                }
            }
        }
    }

    fun getAllVideosFromYouTube() {
        viewModelScope.launch {
            getAllVideoUseCase.invoke().collect() { videoList ->
                val tempVideoResult = videoList.getOrNull()
                if (tempVideoResult != null) {
                    youtubeFilmList.value = tempVideoResult.orEmpty()
                    youtubeFilmList.postValue(tempVideoResult.orEmpty())
                    getConnectedList()
                }
            }
        }
    }

    fun getConnectedList() {
        val tempConnectedList = ArrayList<Media>()

        val maxSize = maxOf(youtubeFilmList.value?.size ?: 0, instagramImageList.value?.size ?: 0)

        for (i in 0 until maxSize) {
            if (i < (youtubeFilmList.value?.size ?: 0)) {
                tempConnectedList.add(youtubeFilmList.value?.get(i) ?: Media())
            }
            if (i < (instagramImageList.value?.size ?: 0)) {
                tempConnectedList.add(instagramImageList.value?.get(i) ?: Media())
            }
        }

        connectedList.postValue(tempConnectedList)
    }
}