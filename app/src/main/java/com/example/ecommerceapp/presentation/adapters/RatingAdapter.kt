package com.example.ecommerceapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ItemRatingBinding
import com.example.ecommerceapp.presentation.uiutils.GenericDiffUtil

class RatingAdapter: RecyclerView.Adapter<RatingAdapter.RatingViewHolder>() {
    var starList = ArrayList<Boolean>()
    inner class RatingViewHolder(val binding : ItemRatingBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingViewHolder {
        val binding = ItemRatingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RatingViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return starList.size
    }

    override fun onBindViewHolder(holder: RatingViewHolder, position: Int) {

        holder.binding.rating.setImageResource(R.drawable.star)

    }

    fun updateList(newList: List<Boolean>) {
        val diffCallback = GenericDiffUtil(
            oldList = starList,
            newList = newList,
            areItemsSame = { old, new -> old == new },
            areContentsSame = { old, new -> old == new }
        )
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        starList.clear()
        starList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}