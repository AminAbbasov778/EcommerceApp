package com.example.ecommerceapp.domain.usecases.searchresultusecases

import com.example.ecommerceapp.data.model.products.ProductModelItem
import com.example.ecommerceapp.domain.usecases.homeusecases.GetProductsUseCase
import javax.inject.Inject

class FilteredProductsByQueryUseCase @Inject constructor(val getProductsUseCase: GetProductsUseCase) {
    suspend operator fun invoke(query: String): Result<List<ProductModelItem>> {
        val productsResult = getProductsUseCase()
        return if (productsResult.isSuccess) {
            val filteredProducts = productsResult.getOrNull()?.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.category.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
           Result.success(filteredProducts ?: emptyList())
        } else {
            Result.failure(Exception("Failed to get products"))
        }
    }
}