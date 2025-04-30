package com.example.ecommerceapp.domain.interfaces

interface UserPreferencesRepository {
    fun getUsername() : Result<String?>
    fun saveUsername(username : String) : Result<Unit>

}