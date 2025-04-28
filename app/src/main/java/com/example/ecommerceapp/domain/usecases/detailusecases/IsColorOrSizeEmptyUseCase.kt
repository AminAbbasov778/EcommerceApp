package com.example.ecommerceapp.domain.usecases.detailusecases

    class IsColorOrSizeEmptyUseCase {
        operator fun invoke(color: String, size: String): Result<Unit> {
            return when {
                color.isEmpty() -> Result.failure(Exception("Color cannot be empty"))
                size.isEmpty() -> Result.failure(Exception("Size cannot be empty"))
                else -> Result.success(Unit)
            }
        }
    }

