package com.example.ecommerceapp.presentation.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.ecommerceapp.R
import com.squareup.picasso.Picasso

object ImageDataBinding {

    @BindingAdapter("set_image_resource")
    @JvmStatic
    fun setImageResource(imageView: ImageView,imageId : Int){
        imageView.setImageResource(imageId)

    }



    @BindingAdapter("load_url")
    @JvmStatic
    fun loadUrl(imageView: ImageView,url :String){
        Picasso.get().load(url).error(R.color.white).placeholder(R.color.white).into(imageView)

    }
}