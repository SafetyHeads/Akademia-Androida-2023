package com.safetyheads.akademiaandroida.presentation.ui.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.domain.entities.Media
import com.safetyheads.akademiaandroida.domain.entities.Settings
import com.safetyheads.akademiaandroida.domain.repositories.SettingsRepository
import com.safetyheads.akademiaandroida.domain.usecases.GetAllVideoUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetInstagramImageUseCase
import kotlinx.coroutines.launch

class MediaViewModel(
    private val getInstagramImageUseCase: GetInstagramImageUseCase,
    private val getAllVideoUseCase: GetAllVideoUseCase,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val youtubeFilmList: MutableLiveData<List<Media>> = MutableLiveData()
    val instagramImageList: MutableLiveData<List<Media>> = MutableLiveData()
    val connectedList: MediatorLiveData<List<Media>> = MediatorLiveData()

    init {
        getAllVideosFromYouTube()
        getInstagramImage()

        connectedList.addSource(youtubeFilmList) {
            mergeLists.invoke()
        }

        connectedList.addSource(instagramImageList) {
            mergeLists.invoke()
        }
    }

    fun getInstagramImage() {
        viewModelScope.launch {
            getInstagramImageUseCase.invoke().collect() { imageList ->
                if (imageList != null) {
                    instagramImageList.postValue(imageList.getOrNull().orEmpty())
                }
            }
        }
    }

    fun getAllVideosFromYouTube() {
        viewModelScope.launch {
            getAllVideoUseCase.invoke().collect() { videoList ->
                if (videoList != null) {
                    youtubeFilmList.postValue(videoList.getOrNull().orEmpty())
                }
            }
        }
    }

    private val mergeLists = {
        if (youtubeFilmList.value != null
            && instagramImageList.value != null) {
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

    fun readSetting() = settingsRepository.readSetting(Settings.SEND_NOTIFICATIONS_MEDIA)

    fun writeSetting(value: Boolean) =
        settingsRepository.writeSetting(Settings.SEND_NOTIFICATIONS_MEDIA, value)
}