package com.example.ecommerceapp.domain.usecases.homeusecases

import com.example.ecommerceapp.domain.interfaces.CategoryRepository
import com.example.ecommerceapp.domain.models.CategoryModel
import javax.inject.Inject

class CombineCategoriesWithImagesUseCase @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
   private val categoryRepository: CategoryRepository
) {

    suspend operator fun invoke(): Result<List<CategoryModel>> {
        val categoriesResult = getCategoriesUseCase()

        return if (categoriesResult.isSuccess) {
            val categories = categoriesResult.getOrNull()?.reversed()?.map { name ->
                CategoryModel(
                    categoryName = name,
                    images = categoryRepository.getCategoriesImgResId(name)
                )
            } ?: emptyList()

            Result.success(categories)
        } else {
            Result.failure(Exception("It failed to load categories"))
        }
    }
}
