package com.example.ecommerceapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ecommerceapp.databinding.FragmentCartBinding
import com.example.ecommerceapp.presentation.adapters.CartAdapter
import com.example.ecommerceapp.presentation.uistates.UiState
import com.example.ecommerceapp.presentation.uiutils.FormatUtils
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.setGone
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.show
import com.example.ecommerceapp.presentation.viewmodels.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    lateinit var binding: FragmentCartBinding
    val viewModel by viewModels<CartViewModel>()
    lateinit var cartAdapter: CartAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        setupAdapter()
    }


    private fun observe() {
        viewModel.cartProducts.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    binding.loading.setGone()
                    binding.productCount.text = it.data.size.toString()
                    cartAdapter.updateList(it.data)
                    viewModel.sumOfProductsPrices(it.data)
                }

                is UiState.Error -> {
                    binding.loading.setGone()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is UiState.Loading -> binding.loading.show()
            }
        }

        viewModel.product.observe(viewLifecycleOwner) { product ->
            product?.let {
                findNavController().navigate(
                    CartFragmentDirections.actionCartFragmentToDetailFragment(
                        it
                    )
                )
                viewModel.clearProduct()

            }

        }

        viewModel.productCountAndPrice.observe(viewLifecycleOwner){
            viewModel.updateProductCountAndPriceInCart(it)
        }

        viewModel.updatedProductCountAndPrice.observe(viewLifecycleOwner){
            when(it){
                is UiState.Success -> {

                    binding.loading.setGone()
                }

                is UiState.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    binding.loading.setGone()
                }
                is UiState.Loading -> binding.loading.show()
            }

        }

        viewModel.sumOfPrices.observe(viewLifecycleOwner){

            binding.totalAmount.text ="$" + FormatUtils.formatPrice(it)
        }
        viewModel.isProductRemoved.observe(viewLifecycleOwner){
            when(it){
                is UiState.Success -> binding.loading.setGone()
                is UiState.Error -> {
                    binding.loading.setGone()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is UiState.Loading -> binding.loading.show()
            }
        }

    }


    private fun setupAdapter() {
        cartAdapter = CartAdapter(  onProductClick = { product ->
            viewModel.convertCartEntityToProductItemModel(product)
        } , onDecreasingBtnClick =  { cartUiModel ->
            viewModel.decreaseCountAndPrice(cartUiModel)
        }, onIncreasingBtnClick = { cartUiModel ->
            viewModel.increaseCountAndPrice(cartUiModel)
        },
            onRemoveClick = { id ->
                viewModel.deleteProductFromCart(id)
            }
        )

        binding.cartRecView.adapter = cartAdapter
    }
}