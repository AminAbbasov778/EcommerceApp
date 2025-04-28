package com.example.ecommerceapp.domain.interfaces

import com.example.ecommerceapp.data.model.products.ProductModelItem

interface ProductRepository {

    suspend fun getProducts():Result<List<ProductModelItem>>
    suspend fun getProductById(id : Int): Result<ProductModelItem>
    suspend fun getProductsByCategory(category : String):Result<List<ProductModelItem>>
}