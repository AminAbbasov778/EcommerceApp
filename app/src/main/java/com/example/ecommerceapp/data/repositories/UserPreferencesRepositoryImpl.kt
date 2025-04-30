package com.example.ecommerceapp.data.repositories

import android.content.SharedPreferences
import com.example.ecommerceapp.domain.interfaces.UserPreferencesRepository
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(val sharedPreferences: SharedPreferences) : UserPreferencesRepository {
    override fun getUsername(): Result<String?>  {
     return  try {
          val username =  sharedPreferences.getString("username",null)
           Result.success(username)
       }catch (e : Exception) {
           Result.failure(e)
       }
    }
    override fun saveUsername(username : String) : Result<Unit> {
       return try {
           sharedPreferences.edit().putString("username",username).apply()
           Result.success(Unit)
       }catch (e : Exception){
           Result.failure(e)
       }
    }


}