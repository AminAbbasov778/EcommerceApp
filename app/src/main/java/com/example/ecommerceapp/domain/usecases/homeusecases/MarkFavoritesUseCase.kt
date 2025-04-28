package com.example.ecommerceapp.domain.usecases.homeusecases

import com.example.ecommerceapp.data.model.products.ProductModelItem

class MarkFavoritesUseCase {
    operator fun invoke(products: List<ProductModelItem>, favoriteIds: List<Int>): List<ProductModelItem> {
        return products.map { product ->
            product.copy(isFavorite = favoriteIds.contains(product.id))
        }
    }
}