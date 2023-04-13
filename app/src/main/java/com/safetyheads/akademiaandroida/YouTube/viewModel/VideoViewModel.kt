package com.safetyheads.akademiaandroida.YouTube.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.BuildConfig
import com.safetyheads.akademiaandroida.YouTube.entities.video.YouTubeVideoDataClass
import com.safetyheads.akademiaandroida.YouTube.useCases.DateUseCase
import com.safetyheads.akademiaandroida.YouTube.useCases.VideoUseCase
import com.safetyheads.akademiaandroida.network.NetworkResult
import com.safetyheads.akademiaandroida.network.YouTubeApi
import kotlinx.coroutines.launch

class VideoViewModel(
    private val videoUseCase: VideoUseCase,
    private val dateUseCase: DateUseCase
) : ViewModel() {

    private val TAG = "VideoViewModel"

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val videosList: MutableLiveData<ArrayList<YouTubeVideoDataClass>> = MutableLiveData(ArrayList())
    val errorMessage: MutableLiveData<Throwable> = MutableLiveData()
    private val previousFilmDate: MutableLiveData<String> = MutableLiveData(dateUseCase.actualDate())

    init {
        getVideo()
    }

    fun getVideo() {
        viewModelScope.launch {
            videoUseCase.execute(previousFilmDate.value.orEmpty()).collect() { networkResult ->
                when (networkResult) {
                    is NetworkResult.Success -> {
                        isLoading.postValue(false)
                        addElementToList(networkResult.data)
                        updateDate(networkResult.data.items[0].snippet.publishTime)
                    }

                    is NetworkResult.Error -> {
                        isLoading.postValue(false)
                        errorMessage.postValue(networkResult.exception)
                        if (BuildConfig.DEBUG)
                            Log.i(TAG, networkResult.exception.message ?: "Unknown error")
                    }

                    is NetworkResult.Loading -> {
                        isLoading.postValue(true)
                    }
                }
            }
        }
    }

    private fun addElementToList(element: YouTubeVideoDataClass) {
        if (element.items[0].snippet.thumbnails.high.url != YouTubeApi.YOUTUBE_DEFAULT_URL) {
            val tempListVideo: ArrayList<YouTubeVideoDataClass> = videosList.value ?: arrayListOf()
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