package com.example.ecommerceapp.domain.models.cart

class CartModel(
    val id: Int = -1,
    val category: String = "",
    val description: String = "",
    val image: String = "",
    val price: Double = 0.0,
    val title: String = "",
    val size: SizeModel ,
    val color: ColorModel,
    val quantity: Int = -1,
    val rating: RatingModel,
    val isFavorite: Boolean = false,
    val ownerId: String = "-1",
    val ownerName: String = "",
    val ownerImage: Int = 1,
) {
}