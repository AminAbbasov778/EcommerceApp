package com.example.ecommerceapp.domain.usecases.detailusecases

import com.example.ecommerceapp.domain.interfaces.DetailRepository
import com.example.ecommerceapp.domain.models.ProductModel
import javax.inject.Inject

class AddColorAndSizeToProductUseCase @Inject constructor(val detailRepository: DetailRepository) {

    operator fun invoke(product: ProductModel): ProductModel {
        val sizes = detailRepository.getSize()
        val colors = detailRepository.getColor()
        return product.copy(
            color = colors,
            size = sizes
        )
    }
}
