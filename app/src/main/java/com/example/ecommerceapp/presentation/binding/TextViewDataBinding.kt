package com.example.ecommerceapp.presentation.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter

object TextViewDataBinding {
    @BindingAdapter("set_text")
    @JvmStatic
    fun setTextFromResId(view: TextView, resId: Int?) {
        resId?.takeIf { it != 0 }?.let {
            view.setText(it)
        }
    }
}