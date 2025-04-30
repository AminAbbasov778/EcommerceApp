package com.example.ecommerceapp.domain.usecases.detailusecases

import com.example.ecommerceapp.data.model.products.ProductModelItem
import com.example.ecommerceapp.domain.interfaces.DetailRepository
import javax.inject.Inject

class AddColorAndSizeToProductUseCase  @Inject constructor(val detailRepository: DetailRepository) {

    operator fun invoke(product: Result<ProductModelItem>) : Result<ProductModelItem>{
        val sizes = detailRepository.getSize()
        val colors = detailRepository.getColor()

        return if (product.isSuccess) {
            val updatedProduct = product.getOrNull()?.copy(
                color = colors,
                size = sizes
            )
            if (updatedProduct != null) {
                Result.success(updatedProduct)
            } else {
                Result.failure(Exception("Product is null"))
            }
        } else {
            Result.failure(Exception("Failure result"))
        }
    }
}
