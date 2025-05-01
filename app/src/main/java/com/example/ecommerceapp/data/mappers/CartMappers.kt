package com.example.ecommerceapp.data.mappers

import com.example.ecommerceapp.data.local.entity.CartEntity
import com.example.ecommerceapp.data.local.entity.ColorEntity
import com.example.ecommerceapp.data.local.entity.RatingEntity
import com.example.ecommerceapp.data.local.entity.SizeEntity
import com.example.ecommerceapp.domain.models.cart.CartModel
import com.example.ecommerceapp.domain.models.cart.ColorModel
import com.example.ecommerceapp.domain.models.cart.RatingModel
import com.example.ecommerceapp.domain.models.cart.SizeModel

fun CartEntity.toDomain(): CartModel{
    return CartModel(id,category,description,image,price,title, SizeModel(size.size,size.sizePosition,size.sizeList),
        ColorModel(color.color,color.colorPosition,color.colorList),quantity, RatingModel(rating.rate,rating.count),isFavorite)
}

fun CartModel.toData(): CartEntity{
    return CartEntity(id,category,description,image,price,title, SizeEntity(size.size,size.sizePosition,size.sizeList),
        ColorEntity(color.color,color.colorPosition,color.colorList),quantity, RatingEntity(rating.rate,rating.count),isFavorite)
}