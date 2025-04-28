package com.example.ecommerceapp.domain.usecases.commonusecases

import com.example.ecommerceapp.data.model.products.ProductModelItem
import com.example.ecommerceapp.domain.interfaces.DetailRepository
import javax.inject.Inject

class AddColorAndSizeToProductsListUseCase @Inject constructor(
    val detailRepository: DetailRepository,
) {

     operator fun invoke(result: Result<List<ProductModelItem>>): Result<List<ProductModelItem>> {
        val sizes = detailRepository.getSize()
        val colors = detailRepository.getColor()

        return if (result.isSuccess) {
            val products = result.getOrNull()?.map { element ->
                element.copy(color = colors, size = sizes)
            } ?: emptyList()
            Result.success(products)
        } else {
            Result.failure(Exception("Failure result"))
        }
    }
}