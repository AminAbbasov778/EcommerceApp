package com.example.ecommerceapp.presentation.uimodels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ChatNavigationUiModel(val ownerImg : Int, val ownerName : String, val ownerId : String,val productId  : String): Parcelable {
}