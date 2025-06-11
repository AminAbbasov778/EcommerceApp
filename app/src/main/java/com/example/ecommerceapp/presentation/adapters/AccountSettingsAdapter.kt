package com.example.ecommerceapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.AccountSettingsItemBinding
import com.example.ecommerceapp.presentation.uimodels.SettingsUiModel
import com.example.ecommerceapp.presentation.uiutils.GenericDiffUtil
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.show

class AccountSettingsAdapter(val onSettingsClick : (SettingsUiModel) -> Unit) : RecyclerView.Adapter<AccountSettingsAdapter.AccountSettingsViewHolder>() {
   val settingsList = ArrayList<SettingsUiModel>()
   lateinit var  context : Context
    inner class AccountSettingsViewHolder(val binding : AccountSettingsItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AccountSettingsViewHolder {
        val binding = AccountSettingsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        context = parent.context
        return AccountSettingsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: AccountSettingsViewHolder,
        position: Int,
    ) {
        holder.binding.settings = settingsList[position]

        holder.binding.settingsCons.setOnClickListener {
            onSettingsClick(settingsList[position])
        }
    }

    override fun getItemCount(): Int {
       return settingsList.size
    }

    fun updateList(newList: List<SettingsUiModel>) {
        val diffCallback = GenericDiffUtil(settingsList,newList, areItemsSame ={old,new -> old.settingName== new.settingName}, areContentsSame = {old,new->old == new})
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        settingsList.clear()
        settingsList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }



}