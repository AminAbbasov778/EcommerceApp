package com.example.ecommerceapp.presentation.uimodels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductUiModel(val category: String,
                          val description: String,
                          val id: Int,
                          val image: String,
                          val price: Double,
                          val rating: RatingUIModel,
                          val title: String,
                          val colorList : ArrayList<String>,
                          val sizeList : ArrayList<String>,
                          var isFavorite : Boolean,
                          val ownerId: String,
                          val ownerName: String,
                          val ownerImage: Int ) : Parcelable{
}