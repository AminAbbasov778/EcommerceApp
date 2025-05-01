package com.example.ecommerceapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ecommerceapp.databinding.ItemCartBinding
import com.example.ecommerceapp.presentation.uimodels.CartUIModel
import com.example.ecommerceapp.presentation.uimodels.UiModel
import com.example.ecommerceapp.presentation.uiutils.GenericDiffUtil

class CartAdapter(val onProductClick : (CartUIModel) -> Unit, val onIncreasingBtnClick : (UiModel) -> Unit, val onDecreasingBtnClick : (UiModel) -> Unit, val onRemoveClick :(Int) -> Unit) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    var productList = ArrayList<CartUIModel>()


    inner class CartViewHolder(val binding : ItemCartBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
       val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return productList.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
       holder.binding.product = productList[position]
        onClick(position,holder)


    }

    fun updateList(newList : List<CartUIModel>){
        val diffCallback = GenericDiffUtil(productList,newList, areItemsSame ={old,new -> old.id== new.id}, areContentsSame = {old,new->old == new})
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        productList.clear()
        productList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }


    fun onClick(position: Int,holder: CartViewHolder){
        val products= productList[position]
        holder.binding.cartItemContainer.setOnClickListener(){
            onProductClick(products)
        }
        holder.binding.increasingBg.setOnClickListener(){
            val count = holder.binding.count.text.toString()
            val price = holder.binding.price.text.toString()
            onIncreasingBtnClick(UiModel(products.id,count,price))
        }
        holder.binding.decreasingBg.setOnClickListener(){
            val count = holder.binding.count.text.toString()
            val price = holder.binding.price.text.toString()
            onDecreasingBtnClick(UiModel(products.id,count,price))
        }
        holder.binding.removeCardView.setOnClickListener {
            onRemoveClick(products.id)
        }
    }



}