package com.example.ecommerceapp.presentation.uiutils

import com.example.ecommerceapp.presentation.uimodels.UiModel

object CartUtils {
    fun increaseCountAndPrice(id: Int, count: String, price: String): UiModel? {
        val intCount = count.toIntOrNull() ?: return null
        val totalPrice = price.toDoubleOrNull() ?: return null
        val unitPrice = totalPrice / intCount
        val newCount = intCount + 1
        val newTotal = unitPrice * newCount
        return UiModel(id, newCount.toString(), newTotal.toString())
    }

    fun decreaseCountAndPrice(id: Int, count: String, price: String): UiModel? {
        val intCount = count.toIntOrNull() ?: return null
        val totalPrice = price.toDoubleOrNull() ?: return null
        if (intCount <= 1) return null
        val unitPrice = totalPrice / intCount
        val newCount = intCount - 1
        val newTotal = unitPrice * newCount
        return UiModel(id, newCount.toString(), newTotal.toString())
    }
}