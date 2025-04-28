package com.example.ecommerceapp.domain.interfaces

interface DetailRepository {
    fun getSize():ArrayList<String>
    fun getColor(): ArrayList<String>
}