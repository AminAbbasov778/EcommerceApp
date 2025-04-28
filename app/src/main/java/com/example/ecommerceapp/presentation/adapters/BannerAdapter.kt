package com.example.ecommerceapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ecommerceapp.databinding.ItemOpportunitySliderBinding
import com.example.ecommerceapp.presentation.uiutils.GenericDiffUtil

class BannerAdapter : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>()  {

    var bannerList = ArrayList<Int>()
    inner class BannerViewHolder(val binding : ItemOpportunitySliderBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding = ItemOpportunitySliderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
         return  BannerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return bannerList.size
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
         holder.binding.sliderImage.setImageResource(bannerList[position])
    }


    fun updateList(newList : ArrayList<Int>){
        val diffCallback = GenericDiffUtil(bannerList,newList, areItemsSame ={old,new -> old== new}, areContentsSame = {old,new->old == new})
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        bannerList.clear()
        bannerList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}