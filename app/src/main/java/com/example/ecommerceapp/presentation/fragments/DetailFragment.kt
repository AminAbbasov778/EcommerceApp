package com.example.ecommerceapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentDetailBinding
import com.example.ecommerceapp.presentation.adapters.ColorsAdapter
import com.example.ecommerceapp.presentation.adapters.RatingAdapter
import com.example.ecommerceapp.presentation.adapters.SizesAdapter
import com.example.ecommerceapp.presentation.uimodels.ChatNavigationUiModel
import com.example.ecommerceapp.presentation.uistates.ResultState
import com.example.ecommerceapp.presentation.uistates.UiState
import com.example.ecommerceapp.presentation.uiutils.FormatUtils
import com.example.ecommerceapp.presentation.uiutils.PicassoUtil.loadUrl
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.setGone
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.show
import com.example.ecommerceapp.presentation.viewmodels.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    lateinit var binding: FragmentDetailBinding
    val args: DetailFragmentArgs by navArgs()
    val viewModel by viewModels<DetailViewModel>()
    val ratingAdapter = RatingAdapter()
    lateinit var colorsAdapter: ColorsAdapter
    lateinit var sizesAdapter: SizesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        viewModel.isProductAddedToCart(args.product.id)
        setData()
        observe()
        onClick()
    }

    private fun setData() {

        colorsAdapter.updateList(args.product.colorList)
        sizesAdapter.updateList(args.product.sizeList)
        viewModel.fetchStars(args.product.rating.rate)
        binding.productImg.loadUrl(args.product.image)
        binding.productTitle.text = args.product.title
        binding.reviewCount.text = "${args.product.rating.count} " + getString(R.string.reviews)
        binding.currency.text = " | " + getString(R.string.dollar)
        binding.descriptionContent.text = args.product.description
        binding.price.text = FormatUtils.formatPrice(args.product.price)
        binding.productOwnerName.text = args.product.ownerName
        binding.productOwnerImg.setImageResource(args.product.ownerImage)


    }


   private fun setupAdapter() {
        sizesAdapter = SizesAdapter { position, size ->
            viewModel.updateProductSizeInCart(args.product.id, size, position)
        }
        colorsAdapter = ColorsAdapter { position, color ->
            viewModel.updateProductColorInCart(args.product.id, color, position)
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
                is UiState.Success -> {
                    viewModel.isProductAddedToCart(args.product.id)
                    binding.loading.setGone()
                }
                is UiState.Error -> uiStateError(it.message)
                is UiState.Loading -> binding.loading.show()
            }
        }

        viewModel.isProductRemoved.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    binding.loading.setGone()
                    viewModel.isProductAddedToCart(args.product.id)
                }
                is UiState.Error -> uiStateError(it.message)
                is UiState.Loading -> binding.loading.show()
            }
        }

        viewModel.isProductInCart.observe(viewLifecycleOwner) { isProdInCart ->
            when (isProdInCart) {
                is UiState.Success -> {

                    if (isProdInCart.data) {
                        binding.buttonName.setText(R.string.remove_from_cart)
                        viewModel.getProductByIdFromCart(args.product.id)

                    } else {

                        binding.buttonName.setText(R.string.add_to_cart)
                    }

                    binding.loading.setGone()
                }

                is UiState.Error -> uiStateError(isProdInCart.message)
                is UiState.Loading -> binding.loading.show()
            }
        }

        viewModel.productCountAndPrice.observe(viewLifecycleOwner) { product ->
            viewModel.updateProductCountAndPriceInCart(product)
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

                is UiState.Error -> {
                    binding.loading.setGone()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is UiState.Loading -> binding.loading.show()
            }
        }

        viewModel.isColorOrSizeEmpty.observe(viewLifecycleOwner) {state ->
            state?.let {
                when (it) {
                    is ResultState.Success -> {
                        val count = binding.count.text.toString().toInt()
                        val sizeState = viewModel.updateProductSize.value
                        val size =
                            if (sizeState is UiState.Success) args.product.sizeList?.let { it[sizeState.data] } else ""
                        val colorState = viewModel.updateProductColor.value
                        val color =
                            if (colorState is UiState.Success) args.product.colorList?.let { it[colorState.data] } else ""
                        val sizePosition = if (sizeState is UiState.Success) sizeState.data else -1
                        val colorPosition =
                            if (colorState is UiState.Success) colorState.data else -1
                        val price = binding.price.text.toString().toDouble()

                        viewModel.insertProductToCart(
                            args.product,
                            color!!,
                            colorPosition,
                            sizePosition,
                            size!!,
                            count,
                            price,
                            args.product.ownerName,
                            args.product.ownerImage,
                            args.product.ownerId
                        )

                    }

                    is ResultState.Error -> Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                        .show()
                }
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
                        setData()
                    }
                }

                is UiState.Error -> uiStateError(state.message)
                is UiState.Loading -> binding.loading.show()
            }
        }
    }

    private fun onClick() {
        binding.addOrRemoveCard.setOnClickListener {
            val value = viewModel.isProductInCart.value
            val isProductInCart = if (value is UiState.Success) value.data else false
            if (isProductInCart) {
                viewModel.deleteProductFromCart(args.product.id)
            } else {
                val sizeState = viewModel.updateProductSize.value
                val size =
                    if (sizeState is UiState.Success) args.product.sizeList?.let { it[sizeState.data] } else ""
                val colorState = viewModel.updateProductColor.value
                val color =
                    if (colorState is UiState.Success) args.product.colorList?.let { it[colorState.data] } else ""
                viewModel.isColorOrSizeEmpty(color!!, size!!)

            }

        }

        binding.chatConstraint.setOnClickListener {
            findNavController().navigate(
                DetailFragmentDirections.actionDetailFragmentToChatFragment(
                    ChatNavigationUiModel(
                        args.product.ownerImage,
                        args.product.ownerName,
                        args.product.ownerId,
                        args.product.id.toString()
                    )
                )
            )
            viewModel.clearData()
        }



        binding.decreasingBg.setOnClickListener {
            val price = binding.price.text.toString()
            val count = binding.count.text.toString()
            viewModel.decreaseCountAndPrice(it.id, count, price)

        }

        binding.increasingBg.setOnClickListener {
            val price = binding.price.text.toString()
            val count = binding.count.text.toString()
            viewModel.increaseCountAndPrice(it.id, count, price)
        }
    }


   private fun uiStateError(message: Int) {
        binding.loading.setGone()
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }



}
