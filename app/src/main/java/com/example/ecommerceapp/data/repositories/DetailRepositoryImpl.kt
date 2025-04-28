package com.example.ecommerceapp.data.repositories

import com.example.ecommerceapp.domain.interfaces.DetailRepository

class DetailRepositoryImpl : DetailRepository{

    override fun getSize(): ArrayList<String>  = arrayListOf("S","M","L","XL")

    override fun getColor(): ArrayList<String>  = arrayListOf("#FFFFFF","#707070","#08E6FF")

}