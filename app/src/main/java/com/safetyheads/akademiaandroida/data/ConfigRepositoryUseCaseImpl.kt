package com.safetyheads.akademiaandroida.data

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.safetyheads.domain.entities.Config
import com.safetyheads.domain.repositories.ConfigRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ConfigRepositoryUseCaseImpl: ConfigRepository {

    private val remoteConfig = FirebaseRemoteConfig.getInstance()
    private val configSettings = FirebaseRemoteConfigSettings.Builder()
        .setFetchTimeoutInSeconds(60)
        .build()

    override suspend fun getConfig(): Flow<Config> {
        remoteConfig.setConfigSettingsAsync(configSettings)

        val versionCode = remoteConfig.getString("versionCode")
        val apiUrl = remoteConfig.getString("apiUrl")
        val config = Config("","")

        remoteConfig.fetchAndActivate().await()
        config.versionCode = versionCode
        config.apiUrl = apiUrl

        return flow {
            this.emit(config)
        }

    }
}