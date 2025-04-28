package com.example.ecommerceapp.presentation.binding

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView

object ColorDataBinding {
    @BindingAdapter("tint_color")
    @JvmStatic
    fun setBackgroundTint(cardView: MaterialCardView,colorString: String) {

            val colorInt = Color.parseColor(colorString)
            cardView.backgroundTintList = ColorStateList.valueOf(colorInt)

    }

    @BindingAdapter("stroke_color")
    @JvmStatic
    fun setStrokeColor(cardView: MaterialCardView,colorString: String) {
        val colorInt = Color.parseColor(colorString)
        cardView.setStrokeColor(ColorStateList.valueOf(colorInt))

    }
}