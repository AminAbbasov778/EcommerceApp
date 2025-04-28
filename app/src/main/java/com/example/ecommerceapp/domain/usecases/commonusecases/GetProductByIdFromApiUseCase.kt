package com.example.ecommerceapp.domain.usecases.commonusecases

import com.example.ecommerceapp.data.model.products.ProductModelItem
import com.example.ecommerceapp.domain.interfaces.ProductRepository
import com.example.ecommerceapp.domain.usecases.detailusecases.AddColorAndSizeToProductUseCase
import javax.inject.Inject

class GetProductByIdFromApiUseCase @Inject constructor(val productRepository: ProductRepository, val addColorAndSizeToProductUseCase: AddColorAndSizeToProductUseCase) {
    suspend operator fun invoke(id : Int) : Result<ProductModelItem> {
       var product =  productRepository.getProductById(id)
      return  addColorAndSizeToProductUseCase(product)
    }
}