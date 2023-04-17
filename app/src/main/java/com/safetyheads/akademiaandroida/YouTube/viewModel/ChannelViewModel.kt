package com.safetyheads.akademiaandroida.YouTube.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.BuildConfig
import com.safetyheads.domain.entities.Channel
import com.safetyheads.domain.usecases.GetChannelUseCase
import kotlinx.coroutines.launch

class ChannelViewModel(
    private val channelUseCase: GetChannelUseCase
) : ViewModel() {

    private val TAG = "ChannelViewModel"

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val channelInfoInformation: MutableLiveData<Channel> = MutableLiveData()
    val errorMessage: MutableLiveData<Throwable> = MutableLiveData()

    init {
        getChannel()
    }

    fun getChannel() {
        isLoading.postValue(true)
        viewModelScope.launch {
            channelUseCase.invoke(GetChannelUseCase.ChannelParam()).collect { networkResult ->
                if (networkResult.isSuccess) {
                    isLoading.postValue(false)
                    channelInfoInformation.postValue(networkResult.getOrNull())
                } else {
                    isLoading.postValue(false)
                    errorMessage.postValue(networkResult.exceptionOrNull())
                    if (BuildConfig.DEBUG)
                        Log.i(TAG, networkResult.exceptionOrNull()?.message ?: "Unknown error")
                }
            }
        }
    }

}