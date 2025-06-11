package com.example.ecommerceapp.domain.usecases.homeusecases

import com.example.ecommerceapp.domain.interfaces.ProductRepository
import com.example.ecommerceapp.domain.models.ProductModel
import com.example.ecommerceapp.domain.usecases.commonusecases.AddColorAndSizeToProductsListUseCase
import com.example.ecommerceapp.presentation.mappers.toDomain
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(val addColorAndSizeToProductsListUseCase: AddColorAndSizeToProductsListUseCase, val productRepository: ProductRepository,val addProductOwnerDataToProductsUseCase: AddProductOwnerDataToProductsUseCase) {

    suspend operator fun invoke(): Result<List<ProductModel>> {
        val productsList = productRepository.getProducts()
      return productsList.map { addColorAndSizeToProductsListUseCase(addProductOwnerDataToProductsUseCase(it)) }
    }


}