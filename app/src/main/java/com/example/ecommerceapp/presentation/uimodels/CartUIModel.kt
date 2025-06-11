package com.example.ecommerceapp.presentation.uimodels

class CartUIModel(
    val id: Int,
    val category: String,
    val description: String,
    val image: String,
    val price: Double,
    val title: String,
    val size: SizeUiModel,
    val color: ColorUiModel,
    val quantity: Int,
    val rating: RatingUIModel,
    val isFavorite: Boolean, val ownerId: String,
    val ownerName: String,
    val ownerImage: Int,
) {
}