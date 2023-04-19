package com.safetyheads.domain.repositories

import com.safetyheads.domain.entities.Settings

interface SettingsRepository {

    //'initSetting' jest jeszcze do ustalenia
//    fun initSetting(map: Map<Settings, String>)

    fun readSetting(setting: Settings): Boolean

    fun writeSetting(setting: Settings, value: Boolean)

}