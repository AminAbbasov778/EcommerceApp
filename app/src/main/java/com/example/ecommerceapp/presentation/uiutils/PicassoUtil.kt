package com.example.ecommerceapp.presentation.uiutils

import android.widget.ImageView
import com.example.ecommerceapp.R
import com.squareup.picasso.Picasso

object PicassoUtil {
    fun ImageView.loadUrl(url :String?){
        Picasso.get().load(url).resize(300, 300).centerCrop().error(R.color.white).placeholder(R.color.white).into(this)
    }
}