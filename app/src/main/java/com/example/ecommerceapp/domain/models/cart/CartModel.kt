package com.example.ecommerceapp.domain.models.cart

class CartModel(val id: Int,
                val category: String,
                val description: String,
                val image: String,
                val price: Double,
                val title: String,
                val size : SizeModel,
                val color : ColorModel,
                val quantity : Int,
                val rating: RatingModel,
                val isFavorite : Boolean
    ) {
}