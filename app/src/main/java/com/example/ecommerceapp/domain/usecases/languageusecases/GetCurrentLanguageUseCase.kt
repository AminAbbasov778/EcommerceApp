package com.example.mynewsapp.domain.usecases.languageusecases

import com.example.ecommerceapp.domain.interfaces.LanguageRepository
import javax.inject.Inject

class GetCurrentLanguageUseCase @Inject constructor(val languageRepository: LanguageRepository) {
    operator fun invoke(): Result<String> = languageRepository.getCurrentLanguage()
}