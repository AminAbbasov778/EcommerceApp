package com.example.ecommerceapp.presentation.uimodels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class RatingUIModel(val rate: Double,
                    val count: Int):Parcelable {
}