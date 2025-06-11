package com.example.ecommerceapp.presentation.mappers

import com.example.ecommerceapp.domain.models.CategoryModel
import com.example.ecommerceapp.domain.models.ProductModel
import com.example.ecommerceapp.presentation.uimodels.CategoryUiModel
import com.example.ecommerceapp.presentation.uimodels.ProductUiModel
import com.example.ecommerceapp.presentation.uimodels.RatingUIModel

fun CategoryModel.toUi(): CategoryUiModel{
    return CategoryUiModel(categoryName,images)
}
fun ProductModel.toUi(): ProductUiModel{

    return ProductUiModel(category,description,id,image,price, RatingUIModel(rating.rate,rating.count),title,color,size,isFavorite,ownerId,ownerName,ownerImage)
}