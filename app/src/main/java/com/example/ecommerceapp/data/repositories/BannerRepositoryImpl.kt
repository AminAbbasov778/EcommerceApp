package com.example.ecommerceapp.data.repositories

import com.example.ecommerceapp.R
import com.example.ecommerceapp.domain.interfaces.BannerRepository

class BannerRepositoryImpl : BannerRepository {
    override fun getBanners(): ArrayList<Int> {
        return arrayListOf<Int>(
            R.drawable.banner8,
            R.drawable.banner5,
            R.drawable.banner12,
            R.drawable.banner6,
            R.drawable.banner11

        )
    }


}