package com.example.ecommerceapp.presentation.adapters

import android.content.Context
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ecommerceapp.R
import com.example.ecommerceapp.data.model.products.ProductModelItem
import com.example.ecommerceapp.databinding.AccountSettingsItemBinding
import com.example.ecommerceapp.presentation.uimodels.SettingsModel
import com.example.ecommerceapp.presentation.uiutils.GenericDiffUtil
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.show

class AccountSettingsAdapter : RecyclerView.Adapter<AccountSettingsAdapter.AccountSettingsViewHolder>() {
   val settingsList = ArrayList<SettingsModel>()
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

        if(settingsList[position].settingName == R.string.language){
            holder.binding.currentChosenSetting.apply {
                show()
                setText(settingsList[position].currentChosenSetting!!)
            }
        }
    }

    override fun getItemCount(): Int {
       return settingsList.size
    }

    fun updateList(newList: List<SettingsModel>) {
        val diffCallback = GenericDiffUtil(settingsList,newList, areItemsSame ={old,new -> old.settingName== new.settingName}, areContentsSame = {old,new->old == new})
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        settingsList.clear()
        settingsList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }



}