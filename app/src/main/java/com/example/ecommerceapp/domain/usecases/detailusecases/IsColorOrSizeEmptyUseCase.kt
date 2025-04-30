package com.example.ecommerceapp.domain.usecases.detailusecases

import javax.inject.Inject

class IsColorOrSizeEmptyUseCase @Inject constructor() {
    operator fun invoke(color: String, size: String): Result<Unit> {
        return when {
            color.isEmpty() -> Result.failure(Exception("Color cannot be empty"))
            size.isEmpty() -> Result.failure(Exception("Size cannot be empty"))
            else -> Result.success(Unit)
        }
    }
}

