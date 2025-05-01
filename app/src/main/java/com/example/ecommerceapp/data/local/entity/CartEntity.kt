package com.example.ecommerceapp.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cart")
data class CartEntity(
    @PrimaryKey
    val id: Int,
    val category: String,
    val description: String,
    val image: String,
    val price: Double,
    val title: String,
   @Embedded val size : SizeEntity,
    @Embedded val color : ColorEntity,
    val quantity : Int,
    @Embedded val rating: RatingEntity,
    val isFavorite : Boolean

    )