package com.example.ecommerceapp.presentation.mappers

import com.example.ecommerceapp.domain.models.cart.CartModel
import com.example.ecommerceapp.domain.models.cart.ColorModel
import com.example.ecommerceapp.domain.models.cart.RatingModel
import com.example.ecommerceapp.domain.models.cart.SizeModel
import com.example.ecommerceapp.presentation.uimodels.CartUIModel
import com.example.ecommerceapp.presentation.uimodels.ProductUiModel

fun ProductUiModel.toDomain(cartUIModel: CartUIModel): CartModel{
    return CartModel(id,category,description,image,price,title, SizeModel(cartUIModel.size.size,cartUIModel.size.sizePosition,cartUIModel.size.sizeList),
        ColorModel(cartUIModel.color.color,cartUIModel.color.colorPosition,colorList),cartUIModel.quantity,
        RatingModel(rating.rate,rating.count),isFavorite,ownerId,ownerName,ownerImage)
}

fun CartUIModel.toDomain(): CartModel{
    return CartModel(id,category,description,image,price,title, SizeModel(size.size,size.sizePosition, size.sizeList),
        ColorModel(color.color,color.colorPosition,color.colorList),quantity, RatingModel(rating.rate,rating.count),isFavorite,ownerId,ownerName,ownerImage)
}