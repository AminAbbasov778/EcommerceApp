package com.example.ecommerceapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.R
import com.example.ecommerceapp.domain.usecases.commonusecases.DeleteProductFromCartUseCase
import com.example.ecommerceapp.domain.usecases.commonusecases.GetProductByIdFromApiUseCase
import com.example.ecommerceapp.domain.usecases.commonusecases.UpdateProductCountAndPriceInCartUseCase
import com.example.ecommerceapp.domain.usecases.detailusecases.CreateRatingStarListUseCase
import com.example.ecommerceapp.domain.usecases.detailusecases.GetProductByIdFromDbUseCase
import com.example.ecommerceapp.domain.usecases.detailusecases.InsertProductToCartUseCase
import com.example.ecommerceapp.domain.usecases.detailusecases.IsColorOrSizeEmptyUseCase
import com.example.ecommerceapp.domain.usecases.detailusecases.IsProductAddedToCartUseCase
import com.example.ecommerceapp.domain.usecases.detailusecases.UpdateProductColorInCartUseCase
import com.example.ecommerceapp.domain.usecases.detailusecases.UpdateProductSizeInCartUseCase
import com.example.ecommerceapp.presentation.mappers.toDomain
import com.example.ecommerceapp.presentation.mappers.toUi
import com.example.ecommerceapp.presentation.uimodels.CartUIModel
import com.example.ecommerceapp.presentation.uimodels.ColorUiModel
import com.example.ecommerceapp.presentation.uimodels.ProductUiModel
import com.example.ecommerceapp.presentation.uimodels.RatingUIModel
import com.example.ecommerceapp.presentation.uimodels.SizeUiModel
import com.example.ecommerceapp.presentation.uimodels.UiModel
import com.example.ecommerceapp.presentation.uistates.ResultState
import com.example.ecommerceapp.presentation.uistates.UiState
import com.example.ecommerceapp.presentation.uiutils.CartUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val createRatingStarListUseCase: CreateRatingStarListUseCase,
    val insertProductToCartUseCase: InsertProductToCartUseCase,
    val deleteProductFromCartUseCase: DeleteProductFromCartUseCase,
    val isProductAddedToCartUseCase: IsProductAddedToCartUseCase,
    val updateProductSizeInCartUseCase: UpdateProductSizeInCartUseCase,
    val updateProductColorInCartUseCase: UpdateProductColorInCartUseCase,
    val updateProductCountAndPriceInCartUseCase: UpdateProductCountAndPriceInCartUseCase,
    val isColorOrSizeEmptyUseCase: IsColorOrSizeEmptyUseCase,
    val getProductByIdFromDbUseCase: GetProductByIdFromDbUseCase,
    val getProductByIdFromApiUseCase: GetProductByIdFromApiUseCase,

    ) : ViewModel() {

    private var _stars = MutableLiveData<List<Boolean>>()
    val stars: LiveData<List<Boolean>> get() = _stars

    private var _isProductInserted = MutableLiveData<UiState<Int>>()
    val isProductInserted: LiveData<UiState<Int>> get() = _isProductInserted

    private var _isProductRemoved = MutableLiveData<UiState<Int>>()
    val isProductRemoved: LiveData<UiState<Int>> get() = _isProductRemoved

    private var _isProductInCart = MutableLiveData<UiState<Boolean>>()
    val isProductInCart: LiveData<UiState<Boolean>> get() = _isProductInCart

    private var _productCountAndPrice = MutableLiveData<UiModel>()
    val productCountAndPrice: LiveData<UiModel> = _productCountAndPrice

    private var _updateProductSize = MutableLiveData<UiState<Int>>()
    val updateProductSize: LiveData<UiState<Int>> get() = _updateProductSize

    private var _updateProductColor = MutableLiveData<UiState<Int>>()
    val updateProductColor: LiveData<UiState<Int>> get() = _updateProductColor

    private var _updatedProductCountAndPrice = MutableLiveData<UiState<UiModel>>()
    val updatedProductCountAndPrice: LiveData<UiState<UiModel>> get() = _updatedProductCountAndPrice

    private var _isColorOrSizeEmpty = MutableLiveData<ResultState<Unit>>()
    val isColorOrSizeEmpty: LiveData<ResultState<Unit>> get() = _isColorOrSizeEmpty

    private var _cartProduct = MutableLiveData<UiState<CartUIModel?>>()
    val cartProduct: LiveData<UiState<CartUIModel?>> get() = _cartProduct

    private var _apiProduct = MutableLiveData<UiState<ProductUiModel?>>()
    val apiProduct: LiveData<UiState<ProductUiModel?>> get() = _apiProduct


    fun fetchStars(rating: Double) {
        _stars.value = createRatingStarListUseCase(rating)

    }

    fun getProductByIdFromApi(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val productResult = getProductByIdFromApiUseCase(id)
            withContext(Dispatchers.Main) {
                if (productResult.isSuccess) {
                    _apiProduct.value = UiState.Success(productResult.getOrNull()?.toUi())
                } else {
                    _apiProduct.value = UiState.Error(R.string.wrong_something)
                }
            }
        }
    }

    fun insertProductToCart(
        product: ProductUiModel,
        color: String,
        colorPosition: Int,
        sizePosition: Int,
        size: String,
        quantity: Int, price: Double
    ) {
        _isProductInserted.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            var result = insertProductToCartUseCase(
                CartUIModel(product.id,product.category,product.description,product.image,price,product.title,
                    SizeUiModel(size,sizePosition,product.sizeList),
                    ColorUiModel(color,colorPosition,product.colorList),quantity,
                    RatingUIModel(product.rating.rate,product.rating.count),product.isFavorite).toDomain()
            )
            withContext(Dispatchers.Main) {
                _isProductInserted.value = when {
                    result.isSuccess -> UiState.Success<Int>(R.string.adding_product_to_cart_successful)
                    result.isFailure -> UiState.Error(R.string.adding_product_to_cart_failure)
                    else -> UiState.Error(R.string.unknown_error)

                }

            }
        }


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

    fun isProductAddedToCart(id: Int) {
        _isProductInCart.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            var result = isProductAddedToCartUseCase(id)
            withContext(Dispatchers.Main) {
                _isProductInCart.value = when {
                    result.isSuccess -> UiState.Success(result.getOrNull() ?: false)
                    result.isFailure -> UiState.Error(R.string.wrong_something)
                    else -> UiState.Error(R.string.unknown_error)
                }

            }
        }

    }

    fun increaseCountAndPrice(id: Int, count: String, price: String) {
        CartUtils.increaseCountAndPrice(id, count, price)?.let { uiModel ->
            _productCountAndPrice.value = uiModel
        }
    }

    fun decreaseCountAndPrice(id: Int, count: String, price: String) {
        CartUtils.decreaseCountAndPrice(id, count, price)?.let { uiModel ->
            _productCountAndPrice.value = uiModel
        }
    }



    fun updateProductSizeInCart(id: Int, size: String, position: Int) {
        val value = (isProductInCart.value as? UiState.Success)?.data ?: false
        if (value) {
            _updateProductSize.value = UiState.Loading
            viewModelScope.launch(Dispatchers.IO) {
                val result = updateProductSizeInCartUseCase(id, size, position)
                withContext(Dispatchers.Main) {
                    _updateProductSize.value = if (result.isSuccess) {
                        UiState.Success<Int>(position)
                    } else {
                        UiState.Error(R.string.failure_to_update_size)
                    }
                }

            }
        } else {
            _updateProductSize.value = UiState.Success<Int>(position)

        }


    }


    fun updateProductColorInCart(id: Int, color: String, position: Int) {
        val value = (isProductInCart.value as? UiState.Success)?.data ?: false
        if (value) {
            _updateProductColor.value = UiState.Loading
            viewModelScope.launch(Dispatchers.IO) {
                val result = updateProductColorInCartUseCase(id, color, position)
                withContext(Dispatchers.Main) {
                    _updateProductColor.value = if (result.isSuccess) {
                        UiState.Success(position)
                    } else {
                        UiState.Error(R.string.failure_to_update_color)
                    }
                }

            }
        } else {
            _updateProductColor.value = UiState.Success(position)
        }
    }

    fun updateProductCountAndPriceInCart(uiModel: UiModel) {
        val value = (isProductInCart.value as? UiState.Success)?.data ?: false
        if (value) {
            val price = uiModel.price.toDouble()
            val count = uiModel.count.toInt()
            _updatedProductCountAndPrice.value = UiState.Loading
            viewModelScope.launch(Dispatchers.IO) {
                val result = updateProductCountAndPriceInCartUseCase(uiModel.id,count,price)
                withContext(Dispatchers.Main) {
                    _updatedProductCountAndPrice.value = if (result.isSuccess) {
                        UiState.Success(uiModel)
                    } else {
                        UiState.Error(R.string.failure_to_update_count)
                    }
                }

            }
        } else {
            _updatedProductCountAndPrice.value = UiState.Success(uiModel)
        }
    }

    fun isColorOrSizeEmpty(color: String, size: String) {
        val result = isColorOrSizeEmptyUseCase(color, size)
        _isColorOrSizeEmpty.value = if (result.isSuccess) {
            ResultState.Success(Unit)
        } else {
            ResultState.Error(R.string.empty_color_or_size)
        }

    }

    fun getProductByIdFromCart(productId: Int) {
        _cartProduct.value = UiState.Loading
        viewModelScope.launch {
            val cartFlow = getProductByIdFromDbUseCase(productId)
            cartFlow.collect { product ->
                _cartProduct.value = if (product.isSuccess) {
                    UiState.Success(product.getOrNull()?.toUi())
                } else {
                    UiState.Error(R.string.process_is_failure)
                }


            }


        }
    }


}