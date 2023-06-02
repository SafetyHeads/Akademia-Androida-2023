package com.safetyheads.akademiaandroida.presentation.ui.fragments.youtube

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.domain.entities.Playlist
import com.safetyheads.akademiaandroida.domain.entities.Video
import com.safetyheads.akademiaandroida.domain.usecases.GetPlayListItemsUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetPlayListsUseCase
import com.safetyheads.akademiaandroida.presentation.BuildConfig
import kotlinx.coroutines.launch

class PlayListViewModel(
    private val playListsUseCase: GetPlayListsUseCase,
    private val playListItemsUseCase: GetPlayListItemsUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "PlayListViewModel"
    }

    val isLoadingPlayLists: MutableLiveData<Boolean> = MutableLiveData(false)
    val listPlayLists: MutableLiveData<ArrayList<Playlist>> = MutableLiveData(ArrayList())
    val errorMessagePlayLists: MutableLiveData<Throwable> = MutableLiveData()

    val isLoadingPlayListItems: MutableLiveData<Boolean> = MutableLiveData(false)
    val listPlayListItems: MutableLiveData<ArrayList<Video>> = MutableLiveData(ArrayList())
    val errorMessagePlayListItems: MutableLiveData<Throwable> = MutableLiveData()

    init {
        getPlayLists()
    }

    fun getPlayLists() {
        isLoadingPlayLists.postValue(true)
        viewModelScope.launch {
            playListsUseCase.invoke().collect { networkResult ->
                if (networkResult.isSuccess) {
                    isLoadingPlayLists.postValue(false)
                    listPlayLists.postValue(networkResult.getOrNull())
                } else {
                    isLoadingPlayLists.postValue(false)
                    errorMessagePlayLists.postValue(networkResult.exceptionOrNull())
                    if (BuildConfig.DEBUG)
                        Log.i(TAG, networkResult.exceptionOrNull()?.message ?: "Unknown error")
                }
            }
        }
    }

    fun getPlayListItems(playListId: String) {
        isLoadingPlayListItems.postValue(true)
        viewModelScope.launch {
            playListItemsUseCase.invoke(GetPlayListItemsUseCase.PlayListItemsParams(playListId)).collect { networkResult ->
                if (networkResult.isSuccess) {
                    isLoadingPlayListItems.postValue(false)
                    listPlayListItems.postValue(networkResult.getOrNull())
                } else {
                    isLoadingPlayListItems.postValue(false)
                    errorMessagePlayListItems.postValue(networkResult.exceptionOrNull())
                    if (BuildConfig.DEBUG)
                        Log.i(TAG, networkResult.exceptionOrNull()?.message ?: "Unknown error")
                }
            }
        }
    }

    fun cleanPlayListItems() {
        listPlayListItems.postValue(ArrayList())
    }
}