package com.example.ecommerceapp.presentation.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.R
import com.example.ecommerceapp.domain.domainstates.ValidationState
import com.example.ecommerceapp.domain.usecases.commonusecases.CapturePhotoUseCase
import com.example.ecommerceapp.domain.usecases.editprofileusecases.UpdateUserProfileUseCase
import com.example.ecommerceapp.domain.usecases.editprofileusecases.UserProfileModelUseCase
import com.example.ecommerceapp.domain.usecases.signupusecases.SaveUsernameUseCase
import com.example.ecommerceapp.domain.usecases.signupusecases.SignupUserUseCase
import com.example.ecommerceapp.domain.usecases.signupusecases.SignupValidationUseCase
import com.example.ecommerceapp.presentation.uistates.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    val signupValidationUseCase: SignupValidationUseCase,
    val signupUserUseCase: SignupUserUseCase,
    val saveUsernameUseCase: SaveUsernameUseCase,
    val userProfileModelUseCase: UserProfileModelUseCase,
    val updateUserProfileUseCase: UpdateUserProfileUseCase,
    val capturePhotoUseCase: CapturePhotoUseCase

    ) : ViewModel() {

    private var _signupValidation = MutableLiveData<UiState<Int>>()
    val signupValidation: LiveData<UiState<Int>> get() = _signupValidation

    private var _signupResult = MutableLiveData<UiState<Int>>()
    val signupResult: LiveData<UiState<Int>> get() = _signupResult

    private val _imageUri = MutableLiveData<Uri>()
    val imageUri: LiveData<Uri> get() = _imageUri

    private val _imagePickerOptions = MutableLiveData<Array<String>>()
    val imagePickerOptions: LiveData<Array<String>> get() = _imagePickerOptions


    fun capturePhoto() {
        _imageUri.value = capturePhotoUseCase()
    }

    fun getImagePickerOptions() {
        _imagePickerOptions.value = arrayOf("Camera", "Gallery")
    }

    fun getSignupValidationResult(userName: String, email: String, password: String) {
        var signupValidationResult = signupValidationUseCase(userName, email, password)
        when (signupValidationResult) {
            is ValidationState.Success -> _signupValidation.value =
                UiState.Success(R.string.successful_validation)

            is ValidationState.Error.EmptyField -> _signupValidation.value =
                UiState.Error(R.string.empty_fields_message)

            is ValidationState.Error.NoLowerCase -> _signupValidation.value =
                UiState.Error(R.string.no_lower_case)

            is ValidationState.Error.NoUpperCase -> _signupValidation.value =
                UiState.Error(R.string.no_upper_case)

            is ValidationState.Error.NoDigit -> _signupValidation.value =
                UiState.Error(R.string.no_digit)

            is ValidationState.Error.NoSpecialChar -> _signupValidation.value =
                UiState.Error(R.string.no_special_character)

            is ValidationState.Error.ShortPassword -> _signupValidation.value =
                UiState.Error(R.string.short_password_message)

            is ValidationState.Error.InvalidEmail -> _signupValidation.value =
                UiState.Error(R.string.invalid_email_message)
        }
    }


    fun signupUser(email: String, password: String, username: String, imageUri: Uri?) {
        _signupResult.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val authResult = signupUserUseCase(email, password)
            if (authResult.isSuccess) {
                val saveResult = saveUsernameUseCase(username)
                val userProfile = userProfileModelUseCase(imageUri, username)
                val updateResult = updateUserProfileUseCase(userProfile)

                withContext(Dispatchers.Main) {
                    if (saveResult.isSuccess && updateResult.isSuccess) {
                        _signupResult.value = UiState.Success(R.string.successful_signup)
                    } else if (!saveResult.isSuccess) {
                        _signupResult.value = UiState.Error(R.string.failure_to_save_username)
                    } else {
                        _signupResult.value = UiState.Error(R.string.failure_updating_profile)
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    _signupResult.value = UiState.Error(R.string.failure_signup)
                }
            }
        }
    }

}