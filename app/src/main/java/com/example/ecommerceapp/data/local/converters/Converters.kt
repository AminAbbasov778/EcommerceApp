package com.example.ecommerceapp.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromStringList(value: ArrayList<String>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): ArrayList<String>? {
        val listType = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson(value, listType)
    }
}