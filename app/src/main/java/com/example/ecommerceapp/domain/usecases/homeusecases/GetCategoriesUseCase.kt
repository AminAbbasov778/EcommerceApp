package com.example.ecommerceapp.domain.usecases.homeusecases

import com.example.ecommerceapp.domain.interfaces.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(): Result<List<String>> = categoryRepository.getCategories()

}
