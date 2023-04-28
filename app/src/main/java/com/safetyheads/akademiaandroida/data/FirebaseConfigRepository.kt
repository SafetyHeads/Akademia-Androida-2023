package com.safetyheads.akademiaandroida.data

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.safetyheads.akademiaandroida.domain.entities.Config
import com.safetyheads.akademiaandroida.domain.repositories.ConfigRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirebaseConfigRepository:
    com.safetyheads.akademiaandroida.domain.repositories.ConfigRepository {

    private val remoteConfig = FirebaseRemoteConfig.getInstance()
    private val configSettings = FirebaseRemoteConfigSettings.Builder()
        .setFetchTimeoutInSeconds(60L)
        .build()

    override fun getConfig(): Flow<com.safetyheads.akademiaandroida.domain.entities.Config> = flow {
        remoteConfig.setConfigSettingsAsync(configSettings).await()

        val isSuccess = remoteConfig.fetchAndActivate().await()
        val versionCode = remoteConfig.getString("versionCode")
        val apiUrl = remoteConfig.getString("apiUrl")

        val isDataDownloaded = versionCode.isNotBlank() && apiUrl.isNotBlank()

        if (isSuccess || isDataDownloaded) {
            emit(com.safetyheads.akademiaandroida.domain.entities.Config(versionCode, apiUrl))
        } else {
            error("Cannot access Config")
        }
    }
}
