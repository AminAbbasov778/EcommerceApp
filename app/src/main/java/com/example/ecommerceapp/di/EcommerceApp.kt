package com.example.ecommerceapp.di

import android.app.Application
import android.content.Context
import com.example.ecommerceapp.utils.LanguageManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EcommerceApp : Application() {
    override fun attachBaseContext(base: Context?) {
        val sharedPreferences = base?.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val language = sharedPreferences?.getString("language", "en") ?: "en"
        val newContext = LanguageManager.setLocale(base!!, language)
        super.attachBaseContext(newContext)
    }

}