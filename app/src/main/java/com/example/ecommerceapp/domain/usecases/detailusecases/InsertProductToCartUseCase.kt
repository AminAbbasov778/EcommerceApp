package com.example.ecommerceapp.domain.usecases.detailusecases

import com.example.ecommerceapp.data.local.entity.ColorEntity
import com.example.ecommerceapp.data.local.entity.SizeEntity
import com.example.ecommerceapp.data.model.products.ProductModelItem
import com.example.ecommerceapp.domain.interfaces.CartDatabaseRepository
import javax.inject.Inject

class InsertProductToCartUseCase @Inject constructor(
    val cartDatabaseRepository: CartDatabaseRepository,
    val convertProductModelItemToCartEntityUseCase: ConvertProductModelItemToCartEntityUseCase,
) {
    suspend operator fun invoke(product: ProductModelItem,color : String,colorPosition : Int,size : String,sizePosition : Int,quantity : Int,price : Double): Result<Unit> {
        val productEntity = convertProductModelItemToCartEntityUseCase(product,color,colorPosition,size,sizePosition,quantity,price)
        return cartDatabaseRepository.insertProduct(productEntity)
    }
}