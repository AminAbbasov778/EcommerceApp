package com.example.ecommerceapp.data.repositories

import com.example.ecommerceapp.data.mappers.toData
import com.example.ecommerceapp.data.mappers.toDomain
import com.example.ecommerceapp.data.model.profile.Profile
import com.example.ecommerceapp.domain.interfaces.UserRepository
import com.example.ecommerceapp.domain.models.ProfileModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(val firebaseAuth: FirebaseAuth,val fireStore : FirebaseFirestore) : UserRepository{
    override suspend fun addProductToFavoriteById(id: Int): Result<Unit> {
        return try {
            val userId = firebaseAuth.currentUser?.uid ?: return Result.failure(Exception("No user"))

            val favoriteData = mapOf("productId" to id)

            fireStore.collection("users").document(userId).collection("favorites").document(id.toString()).set(favoriteData).await()

            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeProductFromFavoriteById(id: Int): Result<Unit> {
       return  try {
           val userId = firebaseAuth.currentUser?.uid ?: return Result.failure(Exception("No user"))
           fireStore.collection("users").document(userId).collection("favorites").document(id.toString()).delete()
           Result.success(Unit)
       }catch (e : Exception){
           Result.failure(e)
       }
    }

    override suspend fun getFavoriteProductsIds(): Flow<Result<List<Int>>> {
        return callbackFlow {
            try {
                val user = firebaseAuth.currentUser

                if (user == null) {
                    trySend(Result.failure(Exception("User is not found")))
                    close()
                    return@callbackFlow
                }

                val listener = fireStore
                    .collection("users")
                    .document(user.uid)
                    .collection("favorites")
                    .addSnapshotListener { snapshot, error ->
                        val result = try {
                            when {
                                error != null -> Result.failure(error)
                                snapshot != null -> {
                                    val ids = snapshot.documents.mapNotNull { it.getLong("productId")?.toInt() }
                                    Result.success(ids)
                                }
                                else -> Result.failure(Exception("Unknown error"))
                            }
                        } catch (e: Exception) {
                            Result.failure(e)
                        }

                        trySend(result)
                    }

                awaitClose { listener.remove() }

            } catch (e: Exception) {
                trySend(Result.failure(e))
                close(e)
            }
        }
    }

    override suspend fun uploadProfileData(profileModel: ProfileModel): Result<Unit> {
        val profile = profileModel.toData()
        val user = firebaseAuth.currentUser
        val userProfile = fireStore.collection("users").document(user?.uid ?: "")
        return try {
            userProfile.set(
                mapOf(
                    "imageBase64" to profile.imageBase64,
                    "username" to profile.username,
                )

            ).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun getProfileData(): Flow<Result<ProfileModel>> = callbackFlow {
        try {
            val user = firebaseAuth.currentUser

            if (user == null) {
                trySend(Result.failure(Exception("No user")))
                close()
                return@callbackFlow
            }

            val listener = fireStore
                .collection("users")
                .document(user.uid)
                .addSnapshotListener { snapshot, error ->

                    val result = try {
                        when {
                            error != null -> Result.failure(error)
                            snapshot != null -> {
                                val profile = snapshot.toObject(Profile::class.java)
                                if (profile != null) Result.success(profile.toDomain())
                                else Result.failure(Exception("Profile not found"))
                            }

                            else -> Result.failure(Exception("Unknown error"))
                        }
                    } catch (e: Exception) {
                        Result.failure(e)
                    }

                    trySend(result)
                }

            awaitClose { listener.remove() }

        } catch (e: Exception) {
            trySend(Result.failure(e))
            close(e)
        }
    }

}