package com.example.ecommerceapp.presentation.mappers

import com.example.ecommerceapp.domain.models.SettingsModel
import com.example.ecommerceapp.presentation.uimodels.SettingsUiModel

fun SettingsModel.toUi(): SettingsUiModel{
    return SettingsUiModel(settingName,currentChosenSetting)
}