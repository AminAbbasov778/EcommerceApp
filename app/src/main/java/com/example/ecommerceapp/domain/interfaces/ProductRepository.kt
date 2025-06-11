package com.example.ecommerceapp.domain.interfaces

import com.example.ecommerceapp.data.model.products.Product
import com.example.ecommerceapp.data.model.products.ProductOwnerData
import com.example.ecommerceapp.domain.models.ProductModel
import com.example.ecommerceapp.domain.models.ProductOwnerDataModel

interface ProductRepository {

    suspend fun getProducts():Result<List<ProductModel>>
    suspend fun getProductById(id : Int): Result<ProductModel>
    suspend fun getProductsByCategory(category : String):Result<List<ProductModel>>
    fun getProductsOwnersData(): Map<String, ProductOwnerDataModel>
}