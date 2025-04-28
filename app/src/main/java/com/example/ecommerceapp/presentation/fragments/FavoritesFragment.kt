package com.example.ecommerceapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentFavoritesBinding
import com.example.ecommerceapp.presentation.adapters.ProductsAdapter
import com.example.ecommerceapp.presentation.uistates.UiState
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.setGone
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.show
import com.example.ecommerceapp.presentation.viewmodels.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
   lateinit var binding : FragmentFavoritesBinding
   val viewModel by viewModels<FavoritesViewModel>()
   var productsAdapter : ProductsAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        observe()
    }

    private fun setupAdapter(){
        productsAdapter = ProductsAdapter(onProductClick = { findNavController().navigate(FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(it,null)) }, onFullFavoriteClick = {viewModel.removeProductFromFavoritesById(it)})
        binding.favoritesRecView.adapter = productsAdapter
    }

    private fun observe(){
        viewModel.favoriteProducts.observe(viewLifecycleOwner){
            when(it){
                is UiState.Success ->{
                    binding.loading.setGone()
                    productsAdapter?.updateList(it.data)
                    binding.favProductCount.text = it.data.size.toString()
                }
                is UiState.Error ->{
                    binding.loading.setGone()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()

                }
                is UiState.Loading -> binding.loading.show()
            }
        }
        viewModel.isProductRemoved.observe(viewLifecycleOwner){
            when(it){
                is UiState.Success -> binding.loading.setGone()
                    is UiState.Error -> {
                        binding.loading.show()
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                is UiState.Loading -> binding.loading.show()

            }
        }
    }


}