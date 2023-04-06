package com.safetyheads.akademiaandroida.data

import android.util.Log
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

        var versionCode = "smth"
        var apiUrl = "smth"

        Log.d("repo", "przed fecthowaniem")

        remoteConfig.fetchAndActivate().addOnCompleteListener {
            if(it.isSuccessful) {
                Log.d("repo", "przed przypisaniem: $versionCode")
                Log.d("repo", "przed przypisaniem: $apiUrl")

                versionCode = remoteConfig.getString("versionCode")
                apiUrl = remoteConfig.getString("apiUrl")
                Log.d("repo", "po przypisaniu: $versionCode")
                Log.d("repo", "po przypisaniu: $apiUrl")
            }
        }.await()

        Log.d("repo", "przy returnie: ${Config(versionCode, apiUrl)}")

        return flow {
            this.emit(Config(versionCode, apiUrl))
        }
    }
}