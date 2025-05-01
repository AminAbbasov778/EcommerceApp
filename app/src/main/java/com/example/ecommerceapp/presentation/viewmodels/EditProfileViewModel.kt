package com.example.ecommerceapp.presentation.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.R
import com.example.ecommerceapp.domain.usecases.commonusecases.CapturePhotoUseCase
import com.example.ecommerceapp.domain.usecases.commonusecases.GetProfileDataUseCase
import com.example.ecommerceapp.domain.usecases.editprofileusecases.ConvertUriToBase64UseCase
import com.example.ecommerceapp.domain.usecases.editprofileusecases.GetUserEmailUseCase
import com.example.ecommerceapp.domain.usecases.editprofileusecases.GetUsernameUseCase
import com.example.ecommerceapp.domain.usecases.editprofileusecases.UpdatePasswordUseCase
import com.example.ecommerceapp.domain.usecases.editprofileusecases.UpdateUserProfileUseCase
import com.example.ecommerceapp.domain.usecases.editprofileusecases.UpdateUsernameUseCase
import com.example.ecommerceapp.presentation.uimodels.NewProfileUiModel
import com.example.ecommerceapp.presentation.uimodels.ProfileUiModel
import com.example.ecommerceapp.presentation.uistates.ResultState
import com.example.ecommerceapp.presentation.uistates.UiState
import com.example.ecommerceapp.presentation.uiutils.ImageUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val getUsernameUseCase: GetUsernameUseCase,
    private val updateUsernameUseCase: UpdateUsernameUseCase,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val capturePhotoUseCase: CapturePhotoUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
    private val getProfileDataUseCase: GetProfileDataUseCase,
    private val imagesBase64UseCase: ConvertUriToBase64UseCase
) : ViewModel() {

    private val _username = MutableLiveData<ResultState<String>>()
    val username: LiveData<ResultState<String>> get() = _username

    private val _email = MutableLiveData<ResultState<String>>()
    val email: LiveData<ResultState<String>> get() = _email

    private val _updateResult = MutableLiveData<UiState<Int>>()
    val updateResult: LiveData<UiState<Int>> get() = _updateResult

    private val _imageUri = MutableLiveData<Uri>()
    val imageUri: LiveData<Uri> get() = _imageUri

    private val _imagePickerOptions = MutableLiveData<Array<String>>()
    val imagePickerOptions: LiveData<Array<String>> get() = _imagePickerOptions

    private val _profileData = MutableLiveData<UiState<ProfileUiModel>>()
    val profileData: LiveData<UiState<ProfileUiModel>> get() = _profileData

    init {
        getUserProfileData()
        getEmail()
    }

    fun capturePhoto() {
        _imageUri.value = capturePhotoUseCase()
    }

    fun getImagePickerOptions() {
        _imagePickerOptions.value = arrayOf("Camera", "Gallery")
    }

    fun getUserProfileData() {
        _profileData.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = getProfileDataUseCase()
            withContext(Dispatchers.Main) {
                result.collect { profileData ->
                    if (profileData.isSuccess) {
                        profileData.getOrNull()?.let { data ->
                            val imageBitmap = ImageUtils.base64ToBitmap(data.imageBase64)
                            _profileData.value = UiState.Success(
                                ProfileUiModel(
                                    imageBitmap = imageBitmap,
                                    username = data.username
                                )
                            )
                        }
                    } else {
                        _profileData.value = UiState.Error(R.string.process_is_failure)
                        getPreferenceUsername()
                    }
                }
            }
        }
    }

    fun updateProfile(username: String, oldPassword: String, newPassword: String, imageUri: Uri?) {
        if (username.isEmpty() && newPassword.isEmpty() && imageUri == null) {
            _updateResult.value = UiState.Error(R.string.please_fill_at_least_one_filed)
            return
        }

        if (username.isNotEmpty()) {
            updatePreferenceUsername(username.trim())
            updateUserProfileFirebaseData(imageUri, username.trim())
        }

        if (oldPassword.isNotEmpty() && newPassword.isNotEmpty()) {
            updatePassword(oldPassword.trim(), newPassword.trim())
        }
    }

    private fun updateUserProfileFirebaseData(imageUri: Uri?, userName: String) {
        _updateResult.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val imageBase64 = imagesBase64UseCase(imageUri)
            val result = updateUserProfileUseCase(NewProfileUiModel(imageBase64,userName))
            withContext(Dispatchers.Main) {
                _updateResult.value =
                    if (result.isSuccess) UiState.Success(R.string.successful_updating_profile)
                    else UiState.Error(R.string.failure_updating_profile)
            }
        }
    }

    private fun getPreferenceUsername() {
        val result = getUsernameUseCase()
        _username.value =
            if (result.isSuccess) ResultState.Success(result.getOrNull() ?: "")
            else ResultState.Error(R.string.failure_username)
    }

    private fun getEmail() {
        val result = getUserEmailUseCase()
        _email.value =
            if (result.isSuccess) ResultState.Success(result.getOrNull() ?: "")
            else ResultState.Error(R.string.failure_email)
    }

    private fun updatePreferenceUsername(newUsername: String) {
        val result = updateUsernameUseCase(newUsername)
        _updateResult.value =
            if (result.isSuccess) UiState.Success(R.string.successful_to_update_username)
            else UiState.Error(R.string.failure_to_update_username)
    }

    private fun updatePassword(currentPassword: String, newPassword: String) {
        _updateResult.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = updatePasswordUseCase(currentPassword, newPassword)
            withContext(Dispatchers.Main) {
                _updateResult.value =
                    if (result.isSuccess) UiState.Success(R.string.successful_to_update_password)
                    else UiState.Error(R.string.failure_to_update_password)
            }
        }
    }
}
