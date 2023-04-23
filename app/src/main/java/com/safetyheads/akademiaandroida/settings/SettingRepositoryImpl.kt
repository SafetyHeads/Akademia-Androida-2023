package com.safetyheads.akademiaandroida.settings

import android.content.Context
import com.safetyheads.domain.entities.Settings
import com.safetyheads.domain.repositories.SettingsRepository

class SettingRepositoryImpl(val context: Context): SettingsRepository {

    private val sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun readSetting(setting: Settings): Boolean {
        return sharedPreferences.getBoolean(setting.type, false)
    }

    override fun writeSetting(setting: Settings, value: Boolean) {
        sharedPreferences.edit().putBoolean(setting.type, value).apply()
    }
}