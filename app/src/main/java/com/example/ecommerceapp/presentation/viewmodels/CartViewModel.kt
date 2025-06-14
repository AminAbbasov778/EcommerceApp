package com.example.ecommerceapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.R
import com.example.ecommerceapp.domain.usecases.cartusecases.GetProductsFromDbUseCase
import com.example.ecommerceapp.domain.usecases.cartusecases.ReverseCartProductsListUseCase
import com.example.ecommerceapp.domain.usecases.cartusecases.SumOfProductsPricesUseCase
import com.example.ecommerceapp.domain.usecases.commonusecases.DeleteProductFromCartUseCase
import com.example.ecommerceapp.domain.usecases.commonusecases.UpdateProductCountAndPriceInCartUseCase
import com.example.ecommerceapp.presentation.mappers.toDomain
import com.example.ecommerceapp.presentation.mappers.toProductUiModel
import com.example.ecommerceapp.presentation.mappers.toUi
import com.example.ecommerceapp.presentation.uimodels.CartUIModel
import com.example.ecommerceapp.presentation.uimodels.ProductUiModel
import com.example.ecommerceapp.presentation.uimodels.UiModel
import com.example.ecommerceapp.presentation.uistates.UiState
import com.example.ecommerceapp.presentation.uiutils.CartUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    val getProductsFromDbUseCase: GetProductsFromDbUseCase,
    val updateProductCountAndPriceInCartUseCase: UpdateProductCountAndPriceInCartUseCase,
    val sumOfProductsPricesUseCase: SumOfProductsPricesUseCase,
    val reverseCartProductsListUseCase: ReverseCartProductsListUseCase,
    val deleteProductFromCartUseCase: DeleteProductFromCartUseCase
) :
    ViewModel() {

    private var _cartProducts = MutableLiveData<UiState<List<CartUIModel>>>()
    val cartProducts: LiveData<UiState<List<CartUIModel>>> get() = _cartProducts

    private var _product = MutableLiveData<ProductUiModel?>()
    val product: LiveData<ProductUiModel?> get() = _product

    private var _updatedProductCountAndPrice = MutableLiveData<UiState<UiModel>>()
    val updatedProductCountAndPrice : LiveData<UiState<UiModel>> get() = _updatedProductCountAndPrice

    private var _productCountAndPrice = MutableLiveData<UiModel>()
    val productCountAndPrice: LiveData<UiModel> get() =  _productCountAndPrice

    private var _sumOfPrices = MutableLiveData<Double>()
    val sumOfPrices : LiveData<Double> get() = _sumOfPrices

    private var _isProductRemoved = MutableLiveData<UiState<Int>>()
    val isProductRemoved: LiveData<UiState<Int>> get() = _isProductRemoved


    init {
        getCartProducts()
    }

    fun deleteProductFromCart(id: Int) {
        _isProductRemoved.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            var result = deleteProductFromCartUseCase(id)
            withContext(Dispatchers.Main) {
                _isProductRemoved.value = when {
                    result.isSuccess -> UiState.Success(R.string.removing_product_to_cart_successful)
                    result.isFailure -> UiState.Error(R.string.removing_product_to_cart_failure)
                    else -> UiState.Error(R.string.unknown_error)
                }
            }


        }

    }


    fun updateProductCountAndPriceInCart(uiModel: UiModel) {
         val count =uiModel.count.toInt()
       val price =  uiModel.price.toDouble()
        val id = uiModel.id
        _updatedProductCountAndPrice.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = updateProductCountAndPriceInCartUseCase(id,count,price)
            withContext(Dispatchers.Main) {
                _updatedProductCountAndPrice.value = if (result.isSuccess) {
                    UiState.Success(uiModel)
                } else {
                    UiState.Error(R.string.failure_to_update_count)
                }
            }

        }

    }


    fun increaseCountAndPrice(uiModel: UiModel) {
        CartUtils.increaseCountAndPrice(uiModel.id, uiModel.count, uiModel.price)?.let { uiModel ->
            _productCountAndPrice.value = uiModel
        }
    }

    fun decreaseCountAndPrice(uiModel: UiModel) {
        CartUtils.decreaseCountAndPrice(uiModel.id, uiModel.count, uiModel.price)?.let { uiModel ->
            _productCountAndPrice.value = uiModel
        }
    }


    fun getCartProducts() {
        _cartProducts.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO){
            val cartFlow = getProductsFromDbUseCase()
            cartFlow.collect { products ->
                withContext(Dispatchers.Main){
                    if(products.isSuccess){
                        val reversedList =  reverseCartProductsListUseCase(products.getOrNull() ?: emptyList())
                        _cartProducts.value = UiState.Success(reversedList.map { it.toUi() })
                    }
                    else {

                        _cartProducts.value = UiState.Error(R.string.process_is_failure)
                    }
                }

            }
        }
    }

    fun convertCartEntityToProductItemModel(product: CartUIModel) {
        _product.value = product.toProductUiModel()
    }

    fun sumOfProductsPrices(products : List<CartUIModel>){
      _sumOfPrices.value =  sumOfProductsPricesUseCase(products.map { it.toDomain() })
    }

    fun clearProduct()  {
        _product.value = null
    }

}