package com.example.ecommerceapp.data.mappers

import com.example.ecommerceapp.data.model.language.Language
import com.example.ecommerceapp.domain.models.LanguageModel

fun Language.toDomain(): LanguageModel{
    return LanguageModel(language,code)
}