package com.example.ecommerceapp.data.remote

import com.example.ecommerceapp.data.model.products.ProductModelItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RequestService {

    @GET("products")
   suspend fun getProducts():Response<List<ProductModelItem>>

   @GET("products/{id}")
   suspend fun getProductsById(@Path("id") id : Int):Response<ProductModelItem>

   @GET("products/categories")
   suspend fun getCategories():Response<List<String>>

   @GET("products/category/{categoryName}")
   suspend fun getProductsByCategory(@Path("categoryName") categoryName : String):Response<List<ProductModelItem>>
}