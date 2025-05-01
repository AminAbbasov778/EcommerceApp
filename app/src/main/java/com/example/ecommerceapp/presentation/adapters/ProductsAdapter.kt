package com.example.ecommerceapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ecommerceapp.databinding.ItemProductBinding
import com.example.ecommerceapp.presentation.uimodels.ProductUiModel
import com.example.ecommerceapp.presentation.uiutils.GenericDiffUtil
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.setGone
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.show

class ProductsAdapter(
    private val onProductClick: (ProductUiModel) -> Unit,
    private val onFullFavoriteClick: (Int) -> Unit,
    private val onEmptyFavoriteClick: (Int) -> Unit = {}
) : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    private var productsList = ArrayList<ProductUiModel>()

    inner class ProductsViewHolder(val binding: ItemProductBinding) : ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        bind(holder.binding,position)
    }


    override fun getItemCount(): Int = productsList.size

    fun updateList(newList: List<ProductUiModel>) {
        val diffCallback = GenericDiffUtil(productsList,newList, areItemsSame ={old,new -> old.id == new.id}, areContentsSame = {old,new->old == new})
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        productsList.clear()
        productsList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun bind(binding: ItemProductBinding,position: Int) {
        val product = productsList[position]
        binding.products = product
        binding.productCardView.setOnClickListener {
            onProductClick(product)
        }

        binding.emptyFavoriteIcon.setOnClickListener {
            onEmptyFavoriteClick(product.id)
        }

        binding.fullFavoriteIcon.setOnClickListener {
            onFullFavoriteClick(product.id)
        }

        if (product.isFavorite) {
            binding.fullFavoriteIcon.show()
            binding.emptyFavoriteIcon.setGone()
        } else {
            binding.fullFavoriteIcon.setGone()
            binding.emptyFavoriteIcon.show()
        }

        binding.executePendingBindings()
    }

}
