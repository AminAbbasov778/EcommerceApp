package com.example.ecommerceapp.presentation.uiutils

import androidx.recyclerview.widget.DiffUtil
import com.example.ecommerceapp.data.model.products.ProductModelItem

class GenericDiffUtil<T>(
    private val oldList: List<T>,
    private val newList: List<T>,
    private val areItemsSame :(old:T,new : T) -> Boolean,
    private val areContentsSame : (old : T,new : T) -> Boolean
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areItemsSame(oldList[oldItemPosition],newList[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areContentsSame(oldList[oldItemPosition],newList[newItemPosition])
    }
}
