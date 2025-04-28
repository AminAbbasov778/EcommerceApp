package com.example.ecommerceapp.domain.interfaces

import com.google.firebase.auth.AuthResult

interface FirebaseAuthRepository {
    suspend fun loginUser(email: String , password :String) : Result<AuthResult>
    suspend fun signupUser(email: String,password: String) : Result<AuthResult>
    fun logout() : Result<Unit>
    fun isUserLoggedIn() : Boolean
    suspend fun updatePassword(currentPassword: String, newPassword: String): Result<Unit>
    fun getCurrentUserEmail(): Result<String?>
}