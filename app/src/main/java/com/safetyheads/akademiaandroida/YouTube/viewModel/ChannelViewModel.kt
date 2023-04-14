package com.safetyheads.akademiaandroida.YouTube.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.BuildConfig
import com.safetyheads.data.network.entities.channel.ChannelDataClass
import com.safetyheads.akademiaandroida.YouTube.useCases.ChannelUseCase
import com.safetyheads.akademiaandroida.network.NetworkResult
import kotlinx.coroutines.launch

class ChannelViewModel(
    private val channelUseCase: ChannelUseCase
) : ViewModel() {

    private val TAG = "ChannelViewModel"

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val channelInformation: MutableLiveData<com.safetyheads.data.network.entities.channel.ChannelDataClass?> = MutableLiveData()
    val errorMessage: MutableLiveData<Throwable> = MutableLiveData()

    init {
        getChannel()
    }

    fun getChannel() {
        viewModelScope.launch {
            channelUseCase.execute().collect() { networkResult ->
                when (networkResult) {
                    is NetworkResult.Success -> {
                        isLoading.postValue(false)
                        if (networkResult.data.items.isNotEmpty()) {
                            channelInformation.postValue(networkResult.data)
                        }
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

}