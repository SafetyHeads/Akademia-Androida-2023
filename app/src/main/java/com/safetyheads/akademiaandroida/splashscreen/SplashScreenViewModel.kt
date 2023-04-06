package com.safetyheads.akademiaandroida.splashscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.domain.entities.Config
import com.safetyheads.domain.usecases.GetConfigUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val splashScreenUseCase: SplashScreenUseCase,
    private val getConfigUseCase: GetConfigUseCase
    ): ViewModel() {

    private val _config: MutableLiveData<Config> = MutableLiveData()
    var config: LiveData<Config> = _config

    val getConfig = viewModelScope.launch{
            getConfigUseCase.invoke().collect { result ->
                    Log.d("viewmodel", "${result.getOrNull()}")
                    _config.postValue(result.getOrNull())
            }
            splashScreenUseCase.delay()
        }
}