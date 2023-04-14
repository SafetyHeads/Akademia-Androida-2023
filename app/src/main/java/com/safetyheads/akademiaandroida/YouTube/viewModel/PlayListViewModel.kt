package com.safetyheads.akademiaandroida.YouTube.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.BuildConfig
import com.safetyheads.data.network.entities.playlistitems.PlayListItemsDataClass
import com.safetyheads.data.network.entities.playlists.PlayListsDataClass
import com.safetyheads.akademiaandroida.YouTube.useCases.PlayListItemsUseCase
import com.safetyheads.akademiaandroida.YouTube.useCases.PlayListsUseCase
import com.safetyheads.akademiaandroida.network.NetworkResult
import kotlinx.coroutines.launch

class PlayListViewModel(
    private val playListsUseCase: PlayListsUseCase,
    private val playListItemsUseCase: PlayListItemsUseCase
): ViewModel() {

    private val TAG = "PlayListViewModel"

    val isLoadingPlayLists: MutableLiveData<Boolean> = MutableLiveData(false)
    val listPlayLists: MutableLiveData<com.safetyheads.data.network.entities.playlists.PlayListsDataClass> = MutableLiveData()
    val errorMessagePlayLists: MutableLiveData<Throwable> = MutableLiveData()

    val isLoadingPlayListItems: MutableLiveData<Boolean> = MutableLiveData(false)
    val listPlayListItems: MutableLiveData<com.safetyheads.data.network.entities.playlistitems.PlayListItemsDataClass?> = MutableLiveData()
    val errorMessagePlayListItems: MutableLiveData<Throwable> = MutableLiveData()

    init {
        getPlayLists()
    }

    fun getPlayLists() {
        viewModelScope.launch {
            playListsUseCase.execute().collect { networkResult ->
                when(networkResult) {
                    is NetworkResult.Success -> {
                        isLoadingPlayLists.postValue(false)
                        listPlayLists.postValue(networkResult.data ?: com.safetyheads.data.network.entities.playlists.PlayListsDataClass())
                    }

                    is NetworkResult.Error -> {
                        isLoadingPlayLists.postValue(false)
                        errorMessagePlayLists.postValue(networkResult.exception)
                        if (BuildConfig.DEBUG)
                            Log.i(TAG, networkResult.exception.message ?: "Unknown error")
                    }

                    is NetworkResult.Loading -> {
                        isLoadingPlayLists.postValue(true)
                    }
                }
            }
        }
    }

    fun getPlayListItems(playListID: String) {
        viewModelScope.launch {
            playListItemsUseCase.execute(playListID).collect { networkResult ->
                when(networkResult) {
                    is NetworkResult.Success -> {
                        isLoadingPlayListItems.postValue(false)
                        listPlayListItems.postValue(networkResult.data)
                    }

                    is NetworkResult.Error -> {
                        isLoadingPlayListItems.postValue(false)
                        errorMessagePlayListItems.postValue(networkResult.exception)
                        if (BuildConfig.DEBUG)
                            Log.i(TAG, networkResult.exception.message ?: "Unknown error")
                    }

                    is NetworkResult.Loading -> {
                        isLoadingPlayListItems.postValue(true)
                    }
                }
            }
        }
    }

    fun cleanPlayListItems() {
        listPlayListItems.postValue(null)
    }
}