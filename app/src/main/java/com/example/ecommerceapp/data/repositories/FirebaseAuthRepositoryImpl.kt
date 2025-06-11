package com.example.ecommerceapp.data.repositories

import com.example.ecommerceapp.domain.interfaces.FirebaseAuthRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(val firebaseAuth: FirebaseAuth) :
    FirebaseAuthRepository {
    override suspend fun loginUser(email: String, password: String): Result<AuthResult> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(authResult)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override suspend fun signupUser(email: String, password: String): Result<AuthResult> {
        return try {
            var result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun logout(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }




    override suspend fun updatePassword(
        currentPassword: String,
        newPassword: String,
    ): Result<Unit> {
        val user =
            firebaseAuth.currentUser ?: return Result.failure(Exception("User not logged in"))
        val credential = EmailAuthProvider.getCredential(user.email ?: "", currentPassword)
        return try {
            user.reauthenticate(credential).await()
            user.updatePassword(newPassword).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override fun getCurrentUserEmail(): Result<String?>  {
       return try {
           val email =  firebaseAuth.currentUser?.email
            Result.success(email)
        }catch (e: Exception){
            Result.failure(e)
        }

    }
    override fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }
}