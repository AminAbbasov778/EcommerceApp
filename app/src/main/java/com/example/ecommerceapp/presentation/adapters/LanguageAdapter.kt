package com.example.ecommerceapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ecommerceapp.databinding.LanguageItemBinding
import com.example.ecommerceapp.presentation.uimodels.LanguageUiModel
import com.example.ecommerceapp.presentation.uiutils.GenericDiffUtil
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.setGone
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.show

class LanguageAdapter(val onLangClick : (LanguageUiModel) -> Unit): RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {

    val languageList = ArrayList<LanguageUiModel>()

    inner class LanguageViewHolder(val binding: LanguageItemBinding) : ViewHolder(binding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): LanguageViewHolder {
        val binding =
            LanguageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: LanguageViewHolder,
        position: Int,
    ) {
        val language = languageList[position]
        holder.binding.lang =language
        holder.binding.languageConstraint.setOnClickListener {
          onLangClick(language)
        }


    }

    override fun getItemCount(): Int {
        return languageList.size
    }

    fun updateList(newList: List<LanguageUiModel>) {
        val diffCallback = GenericDiffUtil(
            oldList = languageList,
            newList = newList,
            areItemsSame = { old, new -> old.language == new.language },
            areContentsSame = { old, new -> old == new }
        )
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        languageList.clear()
        languageList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

}