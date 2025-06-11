package com.example.ecommerceapp.presentation.mappers

import com.example.ecommerceapp.domain.models.cart.CartModel
import com.example.ecommerceapp.presentation.uimodels.CartUIModel
import com.example.ecommerceapp.presentation.uimodels.ColorUiModel
import com.example.ecommerceapp.presentation.uimodels.ProductUiModel
import com.example.ecommerceapp.presentation.uimodels.RatingUIModel
import com.example.ecommerceapp.presentation.uimodels.SizeUiModel


fun CartUIModel.toProductUiModel(): ProductUiModel{
return ProductUiModel(category,description,id,image,price, RatingUIModel(rating.rate,rating.count),title,color.colorList,size.sizeList,isFavorite,ownerId,ownerName,ownerImage)
}

fun CartModel.toUi(): CartUIModel{
    return CartUIModel(id,category,description,image,price,title, SizeUiModel(size.size,size.sizePosition,size.sizeList), ColorUiModel(color.color,color.colorPosition,color.colorList),quantity, RatingUIModel(rating.rate,rating.count),isFavorite,ownerId,ownerName,ownerImage)
}