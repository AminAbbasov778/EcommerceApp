package com.example.ecommerceapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.R
import com.example.ecommerceapp.data.model.products.ProductModelItem
import com.example.ecommerceapp.domain.usecases.commonusecases.GetProductByIdFromApiUseCase
import com.example.ecommerceapp.domain.usecases.favoritesusecases.GetFavoriteProductsIdsUseCase
import com.example.ecommerceapp.domain.usecases.commonusecases.RemoveProductFromFavoritesByIdUseCase
import com.example.ecommerceapp.presentation.uistates.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    val getFavoriteProductsIdsUseCase: GetFavoriteProductsIdsUseCase,
    val getProductByIdFromApiUseCase: GetProductByIdFromApiUseCase,
    val removeProductFromFavoritesByIdUseCase: RemoveProductFromFavoritesByIdUseCase
) : ViewModel() {

    private var _isProductRemoved = MutableLiveData<UiState<Int>>()
    val isProductRemoved: LiveData<UiState<Int>> get() = _isProductRemoved

    private var _favoriteProducts = MutableLiveData<UiState<List<ProductModelItem>>>()
    val favoriteProducts: LiveData<UiState<List<ProductModelItem>>> get() = _favoriteProducts

    init {
        getFavoriteProductsIds()
    }

    fun getFavoriteProductsIds() {
        _favoriteProducts.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = getFavoriteProductsIdsUseCase()
            withContext(Dispatchers.Main) {
                result.collect {
                    if (it.isSuccess) {
                        it.getOrNull()?.let { ids ->
                            getFavoriteProductsById(ids)
                        }

                    } else {
                        _favoriteProducts.value = UiState.Error(R.string.process_is_failure)
                    }
                }
            }
        }
    }

    fun getFavoriteProductsById(ids: List<Int>) {

        viewModelScope.launch(Dispatchers.IO) {
            val products = mutableListOf<ProductModelItem>()
            val error = mutableListOf<Int>()
            coroutineScope {
                val jobs = ids.map { id ->
                    async {
                        val result = getProductByIdFromApiUseCase(id)
                        if (result.isSuccess) {
                            result.getOrNull()?.let {
                                it.isFavorite = true
                                products.add(it) }
                        } else {
                            error.add(R.string.process_is_failure)
                        }
                    }
                }
                jobs.awaitAll()
            }
            withContext(Dispatchers.Main) {
                if(error.isEmpty()){
                    _favoriteProducts.value = UiState.Success(products)
                }
                else{
                    _favoriteProducts.value = UiState.Error(R.string.process_is_failure)
                }

            }

        }
    }



    fun removeProductFromFavoritesById(id : Int){
        viewModelScope.launch (Dispatchers.IO){
            var result =   removeProductFromFavoritesByIdUseCase(id)
            withContext(Dispatchers.Main){
                if(result.isSuccess){
                    _isProductRemoved.value = UiState.Success(R.string.process_is_successfully_remove_from_favorites)
                }
                else{
                    _isProductRemoved.value = UiState.Error(R.string.process_failed_to_remove_from_favorites)
                }
            }
        }
    }








}