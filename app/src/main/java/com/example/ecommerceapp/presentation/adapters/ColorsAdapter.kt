package com.example.ecommerceapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ItemColorBinding
import com.example.ecommerceapp.presentation.uiutils.GenericDiffUtil


class ColorsAdapter(
    private val onColorClick: (Int, String) -> Unit,
) : RecyclerView.Adapter<ColorsAdapter.ColorViewHolder>() {

    private var colorList = ArrayList<String>()
    private var selectedPosition = -1

    inner class ColorViewHolder(val binding: ItemColorBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val binding = ItemColorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ColorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        bind(holder.binding, colorList[position], position)
    }

    override fun getItemCount(): Int = colorList.size

    private fun bind(binding: ItemColorBinding, item: String, position: Int) {
        binding.colors = item

        val context = binding.root.context
        if (selectedPosition == position) {
            binding.colorCardView.strokeWidth = 20
            binding.colorCardView.setStrokeColor(
                ContextCompat.getColor(
                    context,
                    R.color.primary_blue
                )
            )
        } else {
            binding.colorCardView.strokeWidth = 2
            binding.colorCardView.setStrokeColor(
                ContextCompat.getColor(
                    context,
                    R.color.second_text_color
                )
            )
        }

        binding.colorCardView.setOnClickListener {
            val oldPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(oldPosition)
            notifyItemChanged(position)
            onColorClick(position, item)
        }

        binding.executePendingBindings()
    }

    fun updateList(newList: List<String>) {
        val diffCallback = GenericDiffUtil(
            oldList = colorList,
            newList = newList,
            areItemsSame = { old, new -> old == new },
            areContentsSame = { old, new -> old == new }
        )
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        colorList.clear()
        colorList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }


    fun setPosition(position: Int) {
        val oldPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(oldPosition)
        notifyItemChanged(position)
    }
}
