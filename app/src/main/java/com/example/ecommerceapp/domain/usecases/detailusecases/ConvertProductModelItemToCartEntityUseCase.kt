package com.example.ecommerceapp.domain.usecases.detailusecases

import com.example.ecommerceapp.data.local.entity.CartEntity
import com.example.ecommerceapp.data.local.entity.ColorEntity
import com.example.ecommerceapp.data.local.entity.RatingEntity
import com.example.ecommerceapp.data.local.entity.SizeEntity
import com.example.ecommerceapp.data.model.products.ProductModelItem

class ConvertProductModelItemToCartEntityUseCase {

    operator fun invoke(product: ProductModelItem,color:String,colorPosition : Int,size : String,sizePosition : Int,quantity : Int,price : Double): CartEntity {
        return CartEntity(
            id = product.id,
            category = product.category,
            description = product.description,
            image = product.image,
            price = price,
            title = product.title,
            rating = RatingEntity(
                rate = product.rating.rate,
                count = product.rating.count,
            ),
            size = SizeEntity(size,sizePosition) ,
            color = ColorEntity(color,colorPosition),
            quantity = quantity
        )
    }
}