package com.example.ecommerceapp.domain.usecases.searchresultusecases

import com.example.ecommerceapp.domain.interfaces.ProductRepository
import com.example.ecommerceapp.domain.models.ProductModel
import com.example.ecommerceapp.domain.usecases.commonusecases.AddColorAndSizeToProductsListUseCase
import com.example.ecommerceapp.presentation.mappers.toDomain
import javax.inject.Inject

class GetProductsByCategoryUseCase @Inject constructor(
    val productRepository: ProductRepository,
    val addColorAndSizeToProductsListUseCase: AddColorAndSizeToProductsListUseCase,
) {
    suspend operator fun invoke(category: String): Result<List<ProductModel>> {
        val products = productRepository.getProductsByCategory(category)
        return products.map { addColorAndSizeToProductsListUseCase(it) }
    }
}