package com.example.ecommerceapp.data.mappers

import com.example.ecommerceapp.data.model.settings.Settings
import com.example.ecommerceapp.domain.models.SettingsModel

fun Settings.toDomain(): SettingsModel{
    return SettingsModel(settingName,currentChosenSetting)
}