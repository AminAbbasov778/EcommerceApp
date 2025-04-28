package com.example.ecommerceapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ecommerceapp.databinding.PersonalSettingsItemBinding
import com.example.ecommerceapp.presentation.uiutils.GenericDiffUtil

class PersonalSettingsAdapter(val onSettingsClick : (Int) -> Unit) : RecyclerView.Adapter<PersonalSettingsAdapter.SettingsViewHolder>() {
   val settingsList = ArrayList<Int>()
    inner class SettingsViewHolder(val binding : PersonalSettingsItemBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SettingsViewHolder {
        val binding = PersonalSettingsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SettingsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SettingsViewHolder,
        position: Int,
    ) {
       holder.binding.settings = settingsList[position]

        holder.binding.settingsConstraint.setOnClickListener {
            onSettingsClick(settingsList[position])
        }
    }

    override fun getItemCount(): Int {
        return settingsList.size
    }

    fun updateList(newList: List<Int>) {
        val diffCallback = GenericDiffUtil(
            oldList = settingsList,
            newList = newList,
            areItemsSame = { old, new -> old == new },
            areContentsSame = { old, new -> old == new }
        )
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        settingsList.clear()
        settingsList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }



}