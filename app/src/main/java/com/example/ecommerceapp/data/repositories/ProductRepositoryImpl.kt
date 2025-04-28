package com.example.ecommerceapp.data.repositories

import android.util.Log
import com.example.ecommerceapp.data.model.products.ProductModelItem
import com.example.ecommerceapp.data.remote.RequestService
import com.example.ecommerceapp.domain.interfaces.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(val requestService: RequestService) :
    ProductRepository {
    override suspend fun getProducts(): Result<List<ProductModelItem>> {
        return try {
            val response = requestService.getProducts()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("API Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override suspend fun getProductById(id : Int): Result<ProductModelItem> {
        Log.e("yoxla80", "", )
        return try {

            val response = requestService.getProductsById(id)
            Log.e("yoxla68", response.toString(), )
            if(response.isSuccessful){
                Log.e("yoxla69", "getProductByIdFromApi: ", )
                response.body()?.let {
                    Result.success(it)

                } ?: Result.failure(Exception("Empty response body"))

            }
            else{
                Log.e("yoxla70", "getProductByIdFromApi: ", )
                Result.failure(Exception("API Error: ${response.message()}"))
            }
        }catch (e : Exception){
            Log.e("yoxla71", "getProductByIdFromApi: ", )
            Result.failure(e)
        }
    }

    override suspend fun getProductsByCategory(category : String): Result<List<ProductModelItem>> {
        return try {
           val response =  requestService.getProductsByCategory(category)
            if(response.isSuccessful){
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            }
            else{
                Result.failure(Exception("API Error: ${response.message()}"))
            }

        }catch (e:Exception){
            Result.failure(e)
        }
    }
}