package com.example.ecommerceapp.domain.usecases.searchresultusecases

import com.example.ecommerceapp.data.model.products.ProductModelItem
import com.example.ecommerceapp.domain.interfaces.ProductRepository
import com.example.ecommerceapp.domain.usecases.commonusecases.AddColorAndSizeToProductsListUseCase
import javax.inject.Inject

class GetProductsByCategoryUseCase @Inject constructor(val productRepository: ProductRepository,val addColorAndSizeToProductsListUseCase: AddColorAndSizeToProductsListUseCase) {
    suspend operator fun invoke(category : String): Result<List<ProductModelItem>>  {
      val products =  productRepository.getProductsByCategory(category)
      return  addColorAndSizeToProductsListUseCase(products)
    }
}