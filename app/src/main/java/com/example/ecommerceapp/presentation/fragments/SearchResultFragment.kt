package com.example.ecommerceapp.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ecommerceapp.databinding.FragmentSearchResultBinding
import com.example.ecommerceapp.presentation.adapters.ProductsAdapter
import com.example.ecommerceapp.presentation.uistates.UiState
import com.example.ecommerceapp.presentation.viewmodels.SearchResultViewModel
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.setGone
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment : Fragment() {
    lateinit var binding : FragmentSearchResultBinding
    val args : SearchResultFragmentArgs by navArgs()
   lateinit var  productsAdapter : ProductsAdapter
   val viewModel by viewModels<SearchResultViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchResultBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callMethods()
        setupAdapter()
        observe()
        searchEdittextEditor()
    }



    private fun setupAdapter(){
          productsAdapter = ProductsAdapter(onProductClick ={  findNavController().navigate(SearchResultFragmentDirections.actionSearchResultFragmentToDetailFragment(it,null))} , onEmptyFavoriteClick = {},onFullFavoriteClick = {},)



        binding.searchedProductsRecView.adapter = productsAdapter

    }
    private fun observe(){
        viewModel.products.observe(viewLifecycleOwner){
            when(it){
                is UiState.Success -> {
                    productsAdapter.updateList(it.data)
                    binding.loading.setGone()
                }
                is UiState.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    binding.loading.setGone()
                }
                is UiState.Loading -> binding.loading.show()
            }

        }
    }

    private fun searchEdittextEditor(){
        binding.searchEdittext.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.searchEdittext.text.toString()
                viewModel.sendQuery(query)
                true
            }
            else{
                false
            }
        }

    }

    private fun callMethods(){
        if(args.category != null){
            binding.searchEdittext.setText(args.category)
            viewModel.getProductByCategory(args.category!!)
        }
        else{
            binding.searchEdittext.setText(args.query)
            viewModel.filterProductsByQuery(args.query!!)
        }
    }
}