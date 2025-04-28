package com.example.ecommerceapp.data.repositories

import android.util.Log
import com.example.ecommerceapp.data.local.dao.DeleteProductDao
import com.example.ecommerceapp.data.local.dao.InsertProductDao
import com.example.ecommerceapp.data.local.dao.IsProductAddedDao
import com.example.ecommerceapp.data.local.dao.ReadProductsDao
import com.example.ecommerceapp.data.local.dao.UpdateProductCountAndPriceDao
import com.example.ecommerceapp.data.local.dao.UpdateProductDetailDao
import com.example.ecommerceapp.data.local.entity.CartEntity
import com.example.ecommerceapp.domain.interfaces.CartDatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CartDatabaseRepositoryImpl(
    val readProductsDao: ReadProductsDao,
    val writeProductsDao: InsertProductDao,
    val isProductAddedDao: IsProductAddedDao,
    val deleteProductDao: DeleteProductDao,
    val updateProductDetailDao: UpdateProductDetailDao,
    val updateProductCountAndPriceDao: UpdateProductCountAndPriceDao,
) : CartDatabaseRepository {
    override suspend fun readProduct(): Flow<Result<List<CartEntity>>> {
        return try {
            readProductsDao.getProducts().map { products ->
                Result.success(products)
            }
        } catch (e: Exception) {
            flow { emit(Result.failure(e)) }

        }


    }

    override suspend fun insertProduct(product: CartEntity): Result<Unit> {
        return try {
            writeProductsDao.insertProduct(product)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override suspend fun deleteProduct(id: Int): Result<Unit> {
        return try {
            deleteProductDao.deleteProduct(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun isProductAdded(id: Int): Result<Boolean> {
        return try {
            val result = isProductAddedDao.isProductAdded(id)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProductSize(
        id: Int,
        newSize: String,
        sizePosition: Int
    ): Result<Int> {
        return try {
            val result = updateProductDetailDao.updateProductSize(id, newSize,sizePosition)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProductColor(
        id: Int,
        newColor: String,
        colorPosition: Int
    ): Result<Int> {
        return try {
            val result = updateProductDetailDao.updateProductColorById(id, newColor,colorPosition)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProductCountAndPrice(id: Int, count: Int,price : Double): Result<Int> {
        return try {
            var result = updateProductCountAndPriceDao.updateProductCountById(id, count,price)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProductById(productId: Int): Flow<Result<CartEntity>> {
       return try {
             readProductsDao.getProductById(productId).map { product -> Result.success(product) }
       } catch (e :Exception){
          flow {emit(Result.failure(e))   }
       }
    }
}