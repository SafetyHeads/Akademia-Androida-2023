package com.safetyheads.akademiaandroida.data

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.safetyheads.domain.entities.Config
import com.safetyheads.domain.repositories.ConfigRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirebaseConfigRepository: ConfigRepository {
    private val secondsTimeout = 60L

    private val remoteConfig = FirebaseRemoteConfig.getInstance()
    private val configSettings = FirebaseRemoteConfigSettings.Builder()
        .setFetchTimeoutInSeconds(secondsTimeout)
        .build()

    override fun getConfig(): Flow<Config> = flow {
        remoteConfig.setConfigSettingsAsync(configSettings).await()

        val isSuccess = remoteConfig.fetchAndActivate().await()
        val versionCode = remoteConfig.getString("versionCode")
        val apiUrl = remoteConfig.getString("apiUrl")

        val isDataDownloaded = versionCode.isNotBlank() && apiUrl.isNotBlank()

        if (isSuccess || isDataDownloaded) {
            emit(Config(versionCode, apiUrl))
        } else {
            throw Exception("Cannot access Config")
        }
    }
}
