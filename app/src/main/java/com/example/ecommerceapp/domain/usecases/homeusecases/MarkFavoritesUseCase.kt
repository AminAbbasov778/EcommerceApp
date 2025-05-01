package com.example.ecommerceapp.domain.usecases.homeusecases

import com.example.ecommerceapp.domain.models.ProductModel
import javax.inject.Inject

class MarkFavoritesUseCase  @Inject constructor(){
    operator fun invoke(products: List<ProductModel>, favoriteIds: List<Int>): List<ProductModel> {
        return products.map { product ->
            product.copy(isFavorite = favoriteIds.contains(product.id))
        }
    }
}