package com.safetyheads.akademiaandroida.domain.repositories

import com.safetyheads.akademiaandroida.domain.entities.Settings

interface SettingsRepository {

    fun readSetting(setting: Settings): Boolean

    fun writeSetting(setting: Settings, value: Boolean)

}
