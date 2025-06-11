package com.example.ecommerceapp.data.repositories

import android.util.Log
import com.example.ecommerceapp.R
import com.example.ecommerceapp.data.mappers.toDomain
import com.example.ecommerceapp.data.model.products.ProductOwnerData
import com.example.ecommerceapp.data.remote.RequestService
import com.example.ecommerceapp.domain.interfaces.ProductRepository
import com.example.ecommerceapp.domain.models.ProductModel
import com.example.ecommerceapp.domain.models.ProductOwnerDataModel
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(val requestService: RequestService) :
    ProductRepository {
    override suspend fun getProducts(): Result<List<ProductModel>> {
        return try {
            val response = requestService.getProducts()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it.map { it.toDomain() })
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("API Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("xeta", e.localizedMessage.toString(), )
            Result.failure(e)
        }

    }

    override suspend fun getProductById(id: Int): Result<ProductModel> {
        Log.e("yoxla80", "")
        return try {

            val response = requestService.getProductsById(id)
            Log.e("yoxla68", response.toString())
            if (response.isSuccessful) {
                Log.e("yoxla69", "getProductByIdFromApi: ")
                response.body()?.let {
                    Result.success(it.toDomain())

                } ?: Result.failure(Exception("Empty response body"))

            } else {
                Log.e("yoxla70", "getProductByIdFromApi: ")
                Result.failure(Exception("API Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("yoxla71", "getProductByIdFromApi: ")
            Result.failure(e)
        }
    }

    override suspend fun getProductsByCategory(category: String): Result<List<ProductModel>> {
        return try {
            val response = requestService.getProductsByCategory(category)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it.map { it.toDomain() })
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("API Error: ${response.message()}"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getProductsOwnersData(): Map<String, ProductOwnerDataModel> {
       val list =  listOf(
            ProductOwnerData(R.drawable.productowner, "Jake", "1"),
            ProductOwnerData(R.drawable.productowner, "Jake", "2"),
            ProductOwnerData(R.drawable.productowner, "Jake", "3"),
            ProductOwnerData(R.drawable.productowner, "Jake", "4"),
            ProductOwnerData(R.drawable.productowner, "Jake", "5"),
            ProductOwnerData(R.drawable.productowner, "Jake", "6"),
            ProductOwnerData(R.drawable.productowner, "Jake", "7"),
            ProductOwnerData(R.drawable.productowner, "Jake", "8"),
            ProductOwnerData(R.drawable.productowner, "Jake", "9"),
            ProductOwnerData(R.drawable.productowner, "Jake", "10"),
            ProductOwnerData(R.drawable.productowner, "Jake", "11"),
            ProductOwnerData(R.drawable.productowner, "Jake", "12"),
            ProductOwnerData(R.drawable.productowner, "Jake", "13"),
            ProductOwnerData(R.drawable.productowner, "Jake", "14"),
            ProductOwnerData(R.drawable.productowner, "Jake", "15"),
            ProductOwnerData(R.drawable.productowner, "Jake", "16"),
            ProductOwnerData(R.drawable.productowner, "Jake", "17"),
            ProductOwnerData(R.drawable.productowner, "Jake", "18"),
            ProductOwnerData(R.drawable.productowner, "Jake", "19"),
            ProductOwnerData(R.drawable.productowner, "Jake", "20"),
        )

        return list.associateBy { it.ownerId }
            .mapValues { it.value.toDomain() }
    }

}