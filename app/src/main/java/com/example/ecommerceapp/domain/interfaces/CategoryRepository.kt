package com.example.ecommerceapp.domain.interfaces

interface CategoryRepository {

   suspend fun getCategories():Result<List<String>>
   fun getCategoriesImgResId(category: String): List<Int>
}