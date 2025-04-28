package com.example.ecommerceapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerceapp.domain.usecases.commonusecases.IsUserLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel  @Inject constructor(val isUserLoggedInUseCase: IsUserLoggedInUseCase) : ViewModel() {
    private var _isUserLoggedIn = MutableLiveData<Boolean>()
    val isUserLoggedIn : LiveData<Boolean> = _isUserLoggedIn

    init {
        isUserLoggedIn()
    }

    fun isUserLoggedIn(){
        _isUserLoggedIn.value = isUserLoggedInUseCase()
    }

}