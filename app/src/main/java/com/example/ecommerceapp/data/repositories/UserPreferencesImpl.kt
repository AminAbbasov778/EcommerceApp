package com.example.ecommerceapp.data.repositories

import android.content.SharedPreferences
import com.example.ecommerceapp.domain.interfaces.UserPreferences
import javax.inject.Inject

class UserPreferencesImpl @Inject constructor(val sharedPreferences: SharedPreferences) : UserPreferences {
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