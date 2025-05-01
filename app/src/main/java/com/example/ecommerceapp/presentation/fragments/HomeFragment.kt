package com.example.ecommerceapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.ecommerceapp.databinding.FragmentHomeBinding
import com.example.ecommerceapp.presentation.adapters.BannerAdapter
import com.example.ecommerceapp.presentation.adapters.CategoryAdapter
import com.example.ecommerceapp.presentation.adapters.ProductsAdapter
import com.example.ecommerceapp.presentation.uistates.UiState
import com.example.ecommerceapp.presentation.uiutils.ViewPagerUtil
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.setGone
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.show
import com.example.ecommerceapp.presentation.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private val bannerAdapter = BannerAdapter()
    private lateinit var categoryAdapter : CategoryAdapter
    private lateinit var productsAdapter : ProductsAdapter
    private var autoScrollJob: Job? = null
    private var isAutoScrolling = false



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        observe()
        setupAdapters()
        clickOn()
        

    }



    private fun setupAdapters(){
        productsAdapter = ProductsAdapter(onProductClick = {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(it))
        }, onEmptyFavoriteClick = {
            viewModel.addProductToFavoritesById(it)
        }, onFullFavoriteClick = {
            viewModel.removeProductFromFavoritesById(it)
        })
        categoryAdapter = CategoryAdapter {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchResultFragment(null,it))
        }
        binding.productsRecView.adapter = productsAdapter
        binding.categoryRecView.adapter = categoryAdapter

    }


    private fun setupViewPager() {
        binding.opportunityViewPager.adapter = bannerAdapter
        binding.dotsIndicator.setViewPager2(binding.opportunityViewPager)
        binding.opportunityViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                when (state) {
                    ViewPager2.SCROLL_STATE_DRAGGING -> {
                        autoScrollJob?.cancel()
                        isAutoScrolling = false
                    }
                    ViewPager2.SCROLL_STATE_IDLE -> {
                        if (!isAutoScrolling && bannerAdapter.itemCount > 0) {
                            autoScrollJob = ViewPagerUtil.startAutoScroll(
                                binding.opportunityViewPager,
                                bannerAdapter.itemCount,
                                lifecycleScope
                            )
                            isAutoScrolling = true
                        }
                    }
                }
            }
        })
    }

    private fun observe() {
        viewModel.banners.observe(viewLifecycleOwner) { banners ->
            bannerAdapter.updateList(banners)
            autoScrollJob?.cancel()
            isAutoScrolling = false
            if (banners.isNotEmpty()) {
                autoScrollJob = autoScroll(banners.size)
                isAutoScrolling = true
            }
        }

        viewModel.categories.observe(viewLifecycleOwner){
            when(it){

                is UiState.Success -> {
                    categoryAdapter.updateList(it.data)
                    binding.categoryLoading.setGone()
                }
                is UiState.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    binding.categoryLoading.setGone()
                }
                else -> binding.categoryLoading.show()


            }

        }

        viewModel.products.observe(viewLifecycleOwner){
          when(it){
              is UiState.Success -> {
                  productsAdapter.updateList(it.data)
                  binding.productsLoading.setGone()
              }
              is UiState.Error ->{
                  Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                  binding.productsLoading.setGone()
              }
              else -> binding.productsLoading.show()
          }



        }

        viewModel.isProductAddedToFav.observe(viewLifecycleOwner){
            when(it){
                is UiState.Success -> binding.productsLoading.setGone()
                is UiState.Error ->{
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    binding.categoryLoading.setGone()
                }
                is UiState.Loading -> binding.productsLoading.show()
            }
        }

        viewModel.isProductRemovedFromFav.observe(viewLifecycleOwner){
            when(it){
                is UiState.Success -> binding.productsLoading.setGone()


                is UiState.Error ->{
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    binding.categoryLoading.setGone()
                }
                is UiState.Loading -> binding.productsLoading.show()
            }
        }
    }

    private fun clickOn(){
        binding.searchEdittext.setOnClickListener{
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
        }
    }


    private fun autoScroll(itemCount: Int): Job {
        return lifecycleScope.launch {
            while (true) {
                delay(3000)
                val currentItem = binding.opportunityViewPager.currentItem
                if (currentItem == itemCount - 1) {
                    binding.opportunityViewPager.setCurrentItem(0, false)
                } else {
                    binding.opportunityViewPager.setCurrentItem(currentItem + 1, true)
                }
            }
        }
    }





    override fun onDestroyView() {
        super.onDestroyView()
        autoScrollJob?.cancel()
        isAutoScrolling = false
    }
    override fun onPause() {
        super.onPause()
        autoScrollJob?.cancel()
        isAutoScrolling = false
    }

    override fun onResume() {
        super.onResume()
        if (!isAutoScrolling && bannerAdapter.itemCount > 1) {
            autoScrollJob = autoScroll(bannerAdapter.itemCount)
            isAutoScrolling = true
        }
    }





}