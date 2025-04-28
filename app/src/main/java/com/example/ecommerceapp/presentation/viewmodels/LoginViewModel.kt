package com.example.ecommerceapp.presentation.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.R
import com.example.ecommerceapp.domain.domainstates.ValidationState
import com.example.ecommerceapp.domain.usecases.loginusecases.LoginUserUseCase
import com.example.ecommerceapp.domain.usecases.loginusecases.LoginValidationUseCase
import com.example.ecommerceapp.domain.usecases.signupusecases.SaveUsernameUseCase
import com.example.ecommerceapp.presentation.uistates.ResultState
import com.example.ecommerceapp.presentation.uistates.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginValidationUseCase: LoginValidationUseCase,
    val loginUserUseCase: LoginUserUseCase,

) :
    ViewModel() {

    private var _loginValidation = MutableLiveData<UiState<Int>>()
    val loginValidation: LiveData<UiState<Int>> get() = _loginValidation

    private var _loginResult = MutableLiveData<UiState<Int>>()
    val loginResult: LiveData<UiState<Int>> get() = _loginResult




    fun getLoginValidationResult(email: String, password: String) {
        var loginValidationResult = loginValidationUseCase(email, password)
        when (loginValidationResult) {
            is ValidationState.Success -> _loginValidation.value =
                UiState.Success(
                    R.string.successful_validation
                )

            is ValidationState.Error.InvalidEmail -> _loginValidation.value =
                UiState.Error(
                    R.string.invalid_email_message
                )

            is ValidationState.Error.EmptyField -> _loginValidation.value =
                UiState.Error(
                    R.string.empty_fields_message
                )

            is ValidationState.Error.ShortPassword -> _loginValidation.value =
                UiState.Error(
                    R.string.short_password_message
                )

            else -> _loginValidation.value = UiState.Error(R.string.failure_validation)

        }
    }

    fun loginUser(email: String, password: String) {
        _loginResult.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val authResult = loginUserUseCase(email, password)
            withContext(Dispatchers.Main) {
                _loginResult.value =
                    if (authResult.isSuccess) UiState.Success(R.string.successful_login)
                    else UiState.Error(R.string.failure_login)

            }
        }

    }






}