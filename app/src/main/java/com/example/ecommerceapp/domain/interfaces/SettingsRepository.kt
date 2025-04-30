package com.example.ecommerceapp.domain.interfaces

import com.example.ecommerceapp.presentation.uimodels.SettingsModel

interface SettingsRepository {
    fun getPersonalSettingsItems(): List<Int>
    fun  getAccountSettingsItems(): List<SettingsModel>
}