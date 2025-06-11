package com.example.ecommerceapp.domain.usecases.homeusecases

import com.example.ecommerceapp.domain.interfaces.ProductRepository
import com.example.ecommerceapp.domain.models.ProductModel
import com.example.ecommerceapp.domain.models.ProductOwnerDataModel
import javax.inject.Inject

class AddProductOwnerDataToProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(products: List<ProductModel>): List<ProductModel> {
        val ownersMap   =
            productRepository.getProductsOwnersData()

        return products.map { product ->
            val owner   = ownersMap[product.id.toString()]
            product.copy(
                ownerName = owner?.ownerName ?: "",
                ownerImage = owner?.ownerImg ?: 1,
                ownerId = owner?.ownerId ?: "1"
            )
        }
    }
}
