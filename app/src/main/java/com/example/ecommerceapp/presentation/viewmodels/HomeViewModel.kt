package com.example.ecommerceapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.R
import com.example.ecommerceapp.domain.usecases.commonusecases.RemoveProductFromFavoritesByIdUseCase
import com.example.ecommerceapp.domain.usecases.favoritesusecases.AddProductToFavoritesByIdUseCase
import com.example.ecommerceapp.domain.usecases.favoritesusecases.GetFavoriteProductsIdsUseCase
import com.example.ecommerceapp.domain.usecases.homeusecases.CombineCategoriesWithImagesUseCase
import com.example.ecommerceapp.domain.usecases.homeusecases.GetBannersUseCase
import com.example.ecommerceapp.domain.usecases.homeusecases.GetProductsUseCase
import com.example.ecommerceapp.domain.usecases.homeusecases.MarkFavoritesUseCase
import com.example.ecommerceapp.presentation.mappers.toUi
import com.example.ecommerceapp.presentation.uimodels.CategoryUiModel
import com.example.ecommerceapp.presentation.uimodels.ProductUiModel
import com.example.ecommerceapp.presentation.uistates.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getBannersUseCase: GetBannersUseCase,
    var combineCategoriesWithImagesUseCase: CombineCategoriesWithImagesUseCase,
    val getProductsUseCase: GetProductsUseCase,
    val addProductToFavoritesByIdUseCase: AddProductToFavoritesByIdUseCase,
    val removeProductFromFavoritesByIdUseCase: RemoveProductFromFavoritesByIdUseCase,
    val getFavoriteProductsIdsUseCase: GetFavoriteProductsIdsUseCase,
    val markFavoritesUseCase: MarkFavoritesUseCase,
) : ViewModel() {

    private var _banners = MutableLiveData<ArrayList<Int>>()
    val banners: LiveData<ArrayList<Int>> get() = _banners

    private var _categories = MutableLiveData<UiState<List<CategoryUiModel>>>()
    val categories: LiveData<UiState<List<CategoryUiModel>>> get() = _categories

    private var _products = MutableLiveData<UiState<List<ProductUiModel>>>()
    val products: LiveData<UiState<List<ProductUiModel>>> get() = _products

    private var _isProductAddedToFav = MutableLiveData<UiState<Int>>()
    val isProductAddedToFav: LiveData<UiState<Int>> get() = _isProductAddedToFav

    private var _isProductRemovedFromFav = MutableLiveData<UiState<Int>>()
    val isProductRemovedFromFav: LiveData<UiState<Int>> get() = _isProductRemovedFromFav


    init {
        getCategories()
        getBanners()
        getProducts()

    }

    fun getBanners() {
        _banners.value = getBannersUseCase()
    }

    fun getCategories() {
        _categories.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val categoriesResult = combineCategoriesWithImagesUseCase()
            withContext(Dispatchers.Main) {
                _categories.value = when {
                    categoriesResult.isSuccess -> UiState.Success(categoriesResult.getOrNull()?.map { it.toUi() } ?: emptyList())
                    categoriesResult.isFailure -> UiState.Error(R.string.failure_categories)
                    else -> UiState.Error(R.string.unknown_error)

                }
            }

        }
    }

    fun getProducts() {
        _products.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val productsResult = getProductsUseCase()
            val favoriteIdsFlow = getFavoriteProductsIdsUseCase()

            favoriteIdsFlow.collect { favoriteIdsResult ->
                withContext(Dispatchers.Main) {
                    if (productsResult.isSuccess && favoriteIdsResult.isSuccess) {
                        val products = productsResult.getOrNull() ?: emptyList()
                        val favoriteIds = favoriteIdsResult.getOrNull() ?: emptyList()

                        val markedProducts = markFavoritesUseCase(products, favoriteIds)
                        _products.value = UiState.Success(markedProducts.map { it.toUi() })
                    } else if (productsResult.isFailure) {
                        _products.value = UiState.Error(R.string.process_is_failure)
                    } else if (favoriteIdsResult.isFailure) {
                        _products.value = UiState.Error(R.string.process_is_failure)
                    }
                }
            }
        }
    }


    fun addProductToFavoritesById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            var result = addProductToFavoritesByIdUseCase(id)
            withContext(Dispatchers.Main) {
                if (result.isSuccess) {
                    _isProductAddedToFav.value = UiState.Success(id)
                } else {
                    _isProductAddedToFav.value =
                        UiState.Error(R.string.process_failed_to_added_to_favorites)
                }
            }
        }
    }

    fun removeProductFromFavoritesById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            var result = removeProductFromFavoritesByIdUseCase(id)
            withContext(Dispatchers.Main) {
                if (result.isSuccess) {
                    _isProductRemovedFromFav.value = UiState.Success(id)
                } else {
                    _isProductRemovedFromFav.value =
                        UiState.Error(R.string.process_failed_to_remove_from_favorites)
                }
            }
        }
    }


}