package com.example.ecommerceapp.data.mappers

import com.example.ecommerceapp.data.model.category.Category
import com.example.ecommerceapp.data.model.products.Product
import com.example.ecommerceapp.data.model.products.ProductOwnerData
import com.example.ecommerceapp.domain.models.CategoryModel
import com.example.ecommerceapp.domain.models.ProductModel
import com.example.ecommerceapp.domain.models.ProductOwnerDataModel

fun Category.toDomain(): CategoryModel{
    return CategoryModel(categoryName,images)
}

fun Product.toDomain(): ProductModel{
    return ProductModel(category,description,id,image,price,rating,title,color ?: ArrayList(),size ?: ArrayList(),isFavorite ?: false)
}

fun ProductOwnerData.toDomain(): ProductOwnerDataModel{
    return ProductOwnerDataModel(ownerImg,ownerName,ownerId)
}
