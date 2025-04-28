package com.example.ecommerceapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ecommerceapp.data.local.entity.CartEntity
import com.example.ecommerceapp.data.model.category.CategoryModel
import com.example.ecommerceapp.databinding.ItemCategoryBinding
import com.example.ecommerceapp.presentation.uiutils.GenericDiffUtil

class CategoryAdapter(val clickOn : (String) -> Unit) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    var categoryList = ArrayList<CategoryModel>()

    inner class CategoryViewHolder(val binding: ItemCategoryBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.category = categoryList[position]
       holder.binding.categoryCardView.setOnClickListener(){
           clickOn(categoryList[position].categoryName)
       }
    }

    fun updateList(newList : List<CategoryModel>){
        val diffCallback = GenericDiffUtil(categoryList,newList, areItemsSame ={old,new -> old== new}, areContentsSame = {old,new->old == new})
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        categoryList.clear()
        categoryList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }


}