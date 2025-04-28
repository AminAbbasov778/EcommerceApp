package com.example.ecommerceapp.domain.usecases.homeusecases

import com.example.ecommerceapp.domain.interfaces.BannerRepository
import javax.inject.Inject

class GetBannersUseCase @Inject constructor(val bannerRepository: BannerRepository) {

    operator fun invoke() : ArrayList<Int>{
        return bannerRepository.getBanners()
    }
}