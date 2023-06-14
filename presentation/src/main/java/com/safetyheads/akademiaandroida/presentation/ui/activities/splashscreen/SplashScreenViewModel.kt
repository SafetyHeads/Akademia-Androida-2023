package com.safetyheads.akademiaandroida.presentation.ui.activities.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.domain.entities.Config
import com.safetyheads.akademiaandroida.domain.usecases.DelaySplashScreenUseCase
import com.safetyheads.akademiaandroida.domain.usecases.GetConfigUseCase
import com.safetyheads.akademiaandroida.domain.usecases.IsLoggedInUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val splashScreenUseCase: DelaySplashScreenUseCase,
    private val getConfigUseCase: GetConfigUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase
) : ViewModel() {

    private val _config: MutableLiveData<Config> = MutableLiveData()
    private val _failureText: MutableLiveData<String> = MutableLiveData()
    private val _isLoggedIn: MutableLiveData<Boolean> = MutableLiveData()

    var config: LiveData<Config> = _config
    var failureText: LiveData<String> = _failureText
    var isLoggedIn: LiveData<Boolean> = _isLoggedIn

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

    fun checkLoggedIn() {
        viewModelScope.launch {
            isLoggedInUseCase.invoke().collect { result ->
                if (result.isSuccess) {
                    _isLoggedIn.postValue(result.getOrNull())
                }
            }
        }

    }
}
