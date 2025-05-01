package com.example.ecommerceapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.R
import com.example.ecommerceapp.domain.usecases.searchresultusecases.FilteredProductsByQueryUseCase
import com.example.ecommerceapp.domain.usecases.searchresultusecases.GetProductsByCategoryUseCase
import com.example.ecommerceapp.presentation.mappers.toUi
import com.example.ecommerceapp.presentation.uimodels.ProductUiModel
import com.example.ecommerceapp.presentation.uistates.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(val filteredProductsByQueryUseCase: FilteredProductsByQueryUseCase,
    val getProductsByCategoryUseCase: GetProductsByCategoryUseCase
    ) :ViewModel(){

    private var _products = MutableLiveData<UiState<List<ProductUiModel>>>()
    val products : LiveData<UiState<List<ProductUiModel>>> get() = _products


    fun filterProductsByQuery(query : String){
        _products.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = filteredProductsByQueryUseCase(query)
            withContext(Dispatchers.Main){
               val products = result.getOrNull()?.map { it.toUi() } ?: emptyList()
                _products.value = if(result.isSuccess) UiState.Success(products)
                else UiState.Error(R.string.process_is_failure)
            }
        }

    }

    fun sendQuery(query: String){
        val trimmedQuery = query.trim()
        if (trimmedQuery.isNotEmpty()) {
            filterProductsByQuery(query)
        }
    }


    fun getProductByCategory(category : String){
        _products.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = getProductsByCategoryUseCase(category)
            withContext(Dispatchers.Main){
              val list =   result.getOrNull()?.map { it.toUi() } ?: emptyList()
                _products.value = if(result.isSuccess) UiState.Success(list)
                else UiState.Error(R.string.process_is_failure)
            }
        }

    }
}