package com.example.ecommerceapp.domain.usecases.commonusecases

import com.example.ecommerceapp.domain.interfaces.DetailRepository
import com.example.ecommerceapp.domain.models.ProductModel
import javax.inject.Inject

class AddColorAndSizeToProductsListUseCase @Inject constructor(
    val detailRepository: DetailRepository,
) {

    operator fun invoke(products: List<ProductModel>): List<ProductModel> {
        val sizes = detailRepository.getSize()
        val colors = detailRepository.getColor()


        return products.map { element ->
            element.copy(color = colors, size = sizes)
        }

    }
}