package com.example.ecommerceapp.data.mappers

import com.example.ecommerceapp.data.model.category.Category
import com.example.ecommerceapp.data.model.products.Product
import com.example.ecommerceapp.domain.models.CategoryModel
import com.example.ecommerceapp.domain.models.ProductModel

fun Category.toDomain(): CategoryModel{
    return CategoryModel(categoryName,images)
}

fun Product.toDomain(): ProductModel{
    return ProductModel(category,description,id,image,price,rating,title,color,size,isFavorite)
}