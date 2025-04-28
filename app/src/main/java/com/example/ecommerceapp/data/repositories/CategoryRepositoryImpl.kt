package com.example.ecommerceapp.data.repositories

import com.example.ecommerceapp.R
import com.example.ecommerceapp.data.remote.RequestService
import com.example.ecommerceapp.domain.interfaces.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(val requestService: RequestService) :
    CategoryRepository {
    override suspend fun getCategories(): Result<List<String>> {
        return try {
            val response = requestService.getCategories()
            if (response.isSuccessful) {
                response.body()?.let {
                   Result.success(it)
                } ?: Result.failure(Exception("null response"))

            } else {
                Result.failure(Exception("API Error: ${response.message()}"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }


    }
    override fun getCategoriesImgResId(category: String): List<Int> {
        return when (category) {
            "women's clothing" -> arrayListOf(
                R.drawable.clothing1,
                R.drawable.clothing2,
                R.drawable.clothing3,
                R.drawable.clothing4)
            "jewelery" -> arrayListOf(
                R.drawable.jewelery2,
                R.drawable.jewelery3,
                R.drawable.jewelery1,
                R.drawable.jewelery4)
            "men's clothing" -> arrayListOf(
                R.drawable.menclothing1,
                R.drawable.menclothing2,
                R.drawable.menclothing3,
                R.drawable.menclothing4)
            "electronics" -> arrayListOf(
                R.drawable.electronic1,
                R.drawable.electronic2,
                R.drawable.electronic3,
                R.drawable.electronic5)

            else -> emptyList()

        }
    }

}