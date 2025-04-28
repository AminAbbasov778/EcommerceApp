package com.example.ecommerceapp.domain.usecases.homeusecases

import com.example.ecommerceapp.data.model.products.ProductModelItem
import com.example.ecommerceapp.domain.interfaces.ProductRepository
import com.example.ecommerceapp.domain.usecases.commonusecases.AddColorAndSizeToProductsListUseCase
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(val addColorAndSizeToProductsListUseCase: AddColorAndSizeToProductsListUseCase, val productRepository: ProductRepository) {

    suspend operator fun invoke(): Result<List<ProductModelItem>> {
        val productsList = productRepository.getProducts()
      return  addColorAndSizeToProductsListUseCase(productsList)
    }


}