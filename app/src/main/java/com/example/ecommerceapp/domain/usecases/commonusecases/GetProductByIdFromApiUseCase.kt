package com.example.ecommerceapp.domain.usecases.commonusecases

import com.example.ecommerceapp.domain.interfaces.ProductRepository
import com.example.ecommerceapp.domain.models.ProductModel
import com.example.ecommerceapp.domain.usecases.detailusecases.AddColorAndSizeToProductUseCase
import javax.inject.Inject

class GetProductByIdFromApiUseCase @Inject constructor(val productRepository: ProductRepository, val addColorAndSizeToProductUseCase: AddColorAndSizeToProductUseCase,val addProductOwnerToProductUseCase: AddProductOwnerToProductUseCase) {
    suspend operator fun invoke(id : Int) : Result<ProductModel> {

      return  productRepository.getProductById(id).map {  addColorAndSizeToProductUseCase(addProductOwnerToProductUseCase(it)) }
    }
}