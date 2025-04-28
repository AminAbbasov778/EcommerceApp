package com.example.ecommerceapp.domain.interfaces

interface UserPreferences {
    fun getUsername() : Result<String?>
    fun saveUsername(username : String) : Result<Unit>

}