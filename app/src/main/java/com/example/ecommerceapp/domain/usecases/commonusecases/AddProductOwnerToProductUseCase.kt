package com.example.ecommerceapp.domain.usecases.commonusecases

import com.example.ecommerceapp.domain.interfaces.ProductRepository
import com.example.ecommerceapp.domain.models.ProductModel
import javax.inject.Inject

class AddProductOwnerToProductUseCase @Inject constructor(val productRepository: ProductRepository)  {
    operator fun invoke(product: ProductModel): ProductModel {
        val ownersMap  = productRepository.getProductsOwnersData()
         val owner = ownersMap[product.id.toString()]
        return product.copy(ownerId = owner?.ownerId ?: "")
    }
}