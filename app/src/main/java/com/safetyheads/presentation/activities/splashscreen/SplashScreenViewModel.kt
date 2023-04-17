package com.safetyheads.presentation.activities.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.domain.entities.Config
import com.safetyheads.domain.usecases.DelaySplashScreenUseCase
import com.safetyheads.domain.usecases.GetConfigUseCase
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val splashScreenUseCase: DelaySplashScreenUseCase,
    private val getConfigUseCase: GetConfigUseCase
    ): ViewModel() {

    private val _config: MutableLiveData<Config> = MutableLiveData()
    var config: LiveData<Config> = _config

    private val _failureText: MutableLiveData<String> = MutableLiveData()
    var failureText: LiveData<String> = _failureText

    val getConfig = viewModelScope.launch {
        getConfigUseCase.invoke().collect { result ->
            if (result.isSuccess) {
                _config.postValue(result.getOrNull())
                splashScreenUseCase.delay()
            } else {
                _failureText.postValue(result.exceptionOrNull()?.message)
            }
        }
    }

}