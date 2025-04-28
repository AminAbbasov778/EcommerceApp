package com.example.ecommerceapp.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.text.font.Font
import androidx.core.view.marginLeft
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.ecommerceapp.data.model.products.ProductModelItem
import com.example.ecommerceapp.databinding.FragmentDetailBinding
import com.example.ecommerceapp.presentation.adapters.ColorsAdapter
import com.example.ecommerceapp.presentation.adapters.RatingAdapter
import com.example.ecommerceapp.presentation.adapters.SizesAdapter
import com.example.ecommerceapp.presentation.uimodels.ColorUiModel
import com.example.ecommerceapp.presentation.uistates.ResultState
import com.example.ecommerceapp.presentation.uistates.UiState
import com.example.ecommerceapp.presentation.uiutils.FormatUtils
import com.example.ecommerceapp.presentation.viewmodels.DetailViewModel
import com.example.ecommerceapp.presentation.uiutils.PicassoUtil.loadUrl
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.setGone
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    lateinit var binding: FragmentDetailBinding
    val args: DetailFragmentArgs by navArgs()
    val viewModel by viewModels<DetailViewModel>()
    val ratingAdapter = RatingAdapter()
    var product: ProductModelItem? = null
    lateinit var colorsAdapter: ColorsAdapter
    lateinit var sizesAdapter: SizesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        callInitialEvents()
        observe()
        clickOn()
    }

    private fun getProduct() {
        product?.let { prod ->
            prod.color?.let { colorsAdapter.updateList(prod.color) }
            prod.size?.let { sizesAdapter.updateList(prod.size) }
            viewModel.fetchStars(prod.rating.rate)
            binding.productImg.loadUrl(prod.image)
            binding.productTitle.text = prod.title
            binding.reviewCount.text = "${prod.rating.count} reviews"

            binding.currency.text = " | $"
            binding.descriptionContent.text = prod.description
        }
    }

    private fun callInitialEvents() {

        args.product?.let {
            product = it
            viewModel.isProductAddedToCart(it.id)
            binding.price.text = FormatUtils.formatPrice(it.price)
            getProduct()
        } ?: run {
            viewModel.getProductByIdFromApi(args.cartProduct!!.id)
        }

        viewModel.apiProduct.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    state.data?.let {
                        product = it
                        viewModel.fetchStars(it.rating.rate)
                        viewModel.isProductAddedToCart(it.id)
                        binding.loading.setGone()
                        getProduct()
                    }
                }

                is UiState.Error -> {
                    args.cartProduct?.let {
                        product = it
                        viewModel.getProductByIdFromCart(it.id)
                        binding.loading.setGone()
                    }
                }

                is UiState.Loading -> binding.loading.show()
            }
        }
    }

    private fun setupAdapter() {
        sizesAdapter = SizesAdapter { position, size ->
            product?.let { viewModel.updateProductSizeInCart(it.id, size, position) }
        }
        colorsAdapter = ColorsAdapter { position, color ->
            product?.let { viewModel.updateProductColorInCart(it.id, color, position) }
        }
        binding.ratingRecView.adapter = ratingAdapter
        binding.sizeRecView.adapter = sizesAdapter
        binding.colorRecView.adapter = colorsAdapter
    }

    private fun observe() {
        viewModel.stars.observe(viewLifecycleOwner) {
            ratingAdapter.updateList(it)
        }

        viewModel.isProductInserted.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> successfulAddingToCartProcess(it.data)
                is UiState.Error -> uiStateError(it.message)
                is UiState.Loading -> binding.loading.show()
            }
        }

        viewModel.isProductRemoved.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> successfulRemovingFromCartProcess(it.data)
                is UiState.Error -> uiStateError(it.message)
                is UiState.Loading -> binding.loading.show()
            }
        }

        viewModel.isProductInCart.observe(viewLifecycleOwner) { isProdInCart ->
            when (isProdInCart) {
                is UiState.Success -> {

                    if (isProdInCart.data) {
                        binding.buttonName.text = "Remove from Cart"
                        product?.let { viewModel.getProductByIdFromCart(it.id) }

                    } else {

                        binding.buttonName.text = "Add to Cart"
                    }

                    binding.loading.setGone()
                }
                is UiState.Error -> uiStateError(isProdInCart.message)
                is UiState.Loading -> binding.loading.show()
            }
        }

        viewModel.productCountAndPrice.observe(viewLifecycleOwner) {
            product?.let { product -> viewModel.updateProductCountAndPriceInCart(it) }
        }

        viewModel.updateProductSize.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    sizesAdapter.setPosition(it.data)
                    binding.loading.setGone()
                }

                is UiState.Error -> uiStateError(it.message)
                is UiState.Loading -> binding.loading.show()
            }
        }

        viewModel.updateProductColor.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    colorsAdapter.setPosition(it.data)
                    binding.loading.setGone()
                }

                is UiState.Error -> uiStateError(it.message)
                is UiState.Loading -> binding.loading.show()
            }
        }

        viewModel.updatedProductCountAndPrice.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    binding.count.text = it.data.count
                    binding.price.text = it.data.price
                    binding.loading.setGone()
                }

                is UiState.Error -> uiStateSuccess(it.message)
                is UiState.Loading -> binding.loading.show()
            }
        }

        viewModel.isColorOrSizeEmpty.observe(viewLifecycleOwner) {
            when (it) {
                is ResultState.Success -> {
                    val count = binding.count.text.toString().toInt()
                    val sizeState = viewModel.updateProductSize.value
                    val size =
                        if (sizeState is UiState.Success) product?.size?.let { it[sizeState.data] } else ""
                    val colorState = viewModel.updateProductColor.value
                    val color =
                        if (colorState is UiState.Success) product?.color?.let { it[colorState.data] } else ""
                    val sizePosition = if (sizeState is UiState.Success) sizeState.data else -1
                    val colorPosition = if (colorState is UiState.Success) colorState.data else -1
                    val price = binding.price.text.toString().toDouble()

                    product?.let {
                        viewModel.insertProductToCart(
                            it, color!!, colorPosition, sizePosition, size!!, count,price
                        )
                    }
                }

                is ResultState.Error -> Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                    .show()
            }
        }

        viewModel.cartProduct.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    state.data?.let {
                        binding.count.text = it.quantity.toString()
                        sizesAdapter.setPosition(it.size.sizePosition)
                        colorsAdapter.setPosition(it.color.colorPosition)
                        binding.price.text = FormatUtils.formatPrice(it.price)
                        getProduct()
                    }
                }

                is UiState.Error -> uiStateError(state.message)
                is UiState.Loading -> binding.loading.show()
            }
        }
    }

    private fun clickOn() {
        binding.addOrRemoveCard.setOnClickListener {
            val value = viewModel.isProductInCart.value
            val isProductInCart = if(value is UiState.Success) value.data else false
            if(isProductInCart){
                product?.let { viewModel.deleteProductFromCart(it.id) }
            }
            else{
                val sizeState = viewModel.updateProductSize.value
                val size =
                    if (sizeState is UiState.Success) product?.size?.let { it[sizeState.data] } else ""
                val colorState = viewModel.updateProductColor.value
                val color =
                    if (colorState is UiState.Success) product?.color?.let { it[colorState.data] } else ""
                viewModel.isColorOrSizeEmpty(color!!, size!!)

            }

        }



        binding.decreasingBg.setOnClickListener {
            val price = binding.price.text.toString()
            val count = binding.count.text.toString()
            product?.let { viewModel.decreaseCountAndPrice(it.id,count,price) }

        }

        binding.increasingBg.setOnClickListener {
            val price = binding.price.text.toString()
            val count = binding.count.text.toString()
            product?.let { viewModel.increaseCountAndPrice(it.id,count,price) }
        }
    }

    private fun successfulAddingToCartProcess(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        product?.let { viewModel.isProductAddedToCart(it.id) }
        binding.loading.setGone()


    }

    private fun successfulRemovingFromCartProcess(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        binding.loading.setGone()
        product?.let {  viewModel.isProductAddedToCart(it.id) }

    }


    private fun uiStateError(message: Int) {
        binding.loading.setGone()
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun uiStateSuccess(message: Int) {
        binding.loading.setGone()
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
