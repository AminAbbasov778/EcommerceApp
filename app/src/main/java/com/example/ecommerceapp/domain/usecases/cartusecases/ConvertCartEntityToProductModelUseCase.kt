package com.example.ecommerceapp.domain.usecases.cartusecases

import com.example.ecommerceapp.data.local.entity.CartEntity
import com.example.ecommerceapp.data.model.products.Product
import com.example.ecommerceapp.data.model.products.Rating
import javax.inject.Inject

class ConvertCartEntityToProductModelUseCase @Inject constructor()  {

    operator fun invoke(products : CartEntity) : Product{
        return Product(
            category = products.category,
            description = products.description,
            title = products.title,
            id = products.id,
            rating = Rating(products.rating.count,products.rating.rate),
            price = products.price,
            image = products.image,
            color = arrayListOf(products.color.color),
            size = arrayListOf(products.size.size),
            isFavorite = false

        )
    }
}