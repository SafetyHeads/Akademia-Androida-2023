package com.safetyheads.akademiaandroida.presentation.ui.fragments.youtube

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.domain.entities.Video
import com.safetyheads.akademiaandroida.domain.usecases.DateUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetVideoUseCase
import com.safetyheads.akademiaandroida.presentation.BuildConfig
import kotlinx.coroutines.launch

class VideoViewModel(
    private val getVideoUseCase: GetVideoUseCase,
    private val dateUseCase: DateUseCase
) : ViewModel() {

    companion object{
        private const val TAG = "VideoViewModel"
    }

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val videosList: MutableLiveData<ArrayList<Video>> = MutableLiveData(ArrayList())
    val errorMessage: MutableLiveData<Throwable> = MutableLiveData()
    private val previousFilmDate: MutableLiveData<String> = MutableLiveData(dateUseCase.actualDate())

    init {
        getVideo()
    }

    fun getVideo() {
        isLoading.postValue(true)
        viewModelScope.launch {
            getVideoUseCase.invoke(GetVideoUseCase.VideoParam(previousFilmDate.value.orEmpty())).collect { networkResult ->
                if (networkResult.isSuccess) {
                    isLoading.postValue(false)
                    networkResult.getOrNull()?.let { addElementToList(it) }
                    networkResult.getOrNull()?.publishTime?.let { updateDate(it) }
                } else {
                    isLoading.postValue(false)
                    errorMessage.postValue(networkResult.exceptionOrNull())
                    if (BuildConfig.DEBUG)
                        Log.i(TAG, networkResult.exceptionOrNull()?.message ?: "Unknown error")
                }
            }
        }
    }

    private fun addElementToList(element: Video) {
        if (element.videoId.isNotEmpty()) {
            val tempListVideo: ArrayList<Video> = videosList.value ?: arrayListOf()
            tempListVideo.add(element)
            videosList.postValue(tempListVideo)
        } else {
            getVideo()
        }
    }

    private fun updateDate(actualVideoDate: String) {
        previousFilmDate.value = dateUseCase.updateDate(actualVideoDate)
    }
}