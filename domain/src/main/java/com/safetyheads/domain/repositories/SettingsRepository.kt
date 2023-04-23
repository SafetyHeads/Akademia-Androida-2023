package com.safetyheads.domain.repositories

import com.safetyheads.domain.entities.Settings

interface SettingsRepository {

    fun readSetting(setting: Settings): Boolean

    fun writeSetting(setting: Settings, value: Boolean)

}