package com.example.ecommerceapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ItemSizeBinding
import com.example.ecommerceapp.presentation.uiutils.GenericDiffUtil

class SizesAdapter(val clickOn : (position : Int,size : String) -> Unit) : RecyclerView.Adapter<SizesAdapter.SizesViewHolder>() {
    var sizesList = ArrayList<String>()
    var selectedPosition = -1
    inner class SizesViewHolder(val binding : ItemSizeBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizesViewHolder {
        val binding = ItemSizeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SizesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return  sizesList.size
    }

    override fun onBindViewHolder(holder: SizesViewHolder, position: Int) {
        holder.binding.sizes = sizesList[position]

        if (selectedPosition == position) {
            val blueColor =   ContextCompat.getColor(holder.itemView.context, R.color.primary_blue)
            holder.binding.sizeTypeCard.strokeWidth = 20
            holder.binding.sizeTypeCard.setStrokeColor(blueColor)
        } else {

            val color =   ContextCompat.getColor(holder.itemView.context, R.color.second_text_color)
            holder.binding.sizeTypeCard.setStrokeColor(color)
            holder.binding.sizeTypeCard.strokeWidth = 2
        }


        holder.binding.sizeTypeCard.setOnClickListener {
           clickOn(position,sizesList[position])
        }
    }


    fun updateList(newList: List<String>) {
        val diffCallback = GenericDiffUtil(
            oldList = sizesList,
            newList = newList,
            areItemsSame = { old, new -> old == new },
            areContentsSame = { old, new -> old == new }
        )
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        sizesList.clear()
        sizesList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }


    fun setPosition(position: Int) {
        val oldPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(oldPosition)
        notifyItemChanged(position)
    }
}