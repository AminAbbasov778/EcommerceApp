package com.example.mynewsapp.domain.usecases.languageusecases

import android.content.Context
import com.example.ecommerceapp.domain.interfaces.LanguageRepository
import com.example.ecommerceapp.utils.LanguageManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SetLanguageUseCase @Inject constructor(
    private val repository: LanguageRepository,
    @ApplicationContext private val context: Context
) {
    operator fun invoke(language: String): Result<Unit> {
        val result = repository.setLanguage(language)
        return if (result.isSuccess) {
            LanguageManager.setLocale(context, language)
            Result.success(Unit)
        } else result
    }
}
