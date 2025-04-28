package com.example.ecommerceapp.presentation.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter

object RoundedDataBinding {

    @BindingAdapter("rounded_price")
    @JvmStatic
    fun getRoundedPrice(textView: TextView, number: Double) {
        textView.text = String.format("%.2f", number)
    }

    @BindingAdapter("rounded_number")
    @JvmStatic
    fun getRoundedNumber(textView: TextView,number : Double){
        textView.text = Math.round(number).toString()

    }
}