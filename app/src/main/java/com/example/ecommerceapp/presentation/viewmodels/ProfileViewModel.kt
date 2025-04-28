package com.example.ecommerceapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.R
import com.example.ecommerceapp.domain.usecases.commonusecases.GetProfileDataUseCase
import com.example.ecommerceapp.presentation.uimodels.UserProfileUiModel
import com.example.ecommerceapp.presentation.uistates.UiState
import com.example.ecommerceapp.presentation.uiutils.ImageUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(val getProfileDataUseCase: GetProfileDataUseCase) :
    ViewModel() {
    private var _profileData = MutableLiveData<UiState<UserProfileUiModel>>()
    val profileData: LiveData<UiState<UserProfileUiModel>> get() = _profileData

    init {
        getProfileData()
    }

    fun getProfileData() {
        _profileData.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = getProfileDataUseCase()
            withContext(Dispatchers.Main) {
                result.collect { profileData ->
                    if (profileData.isSuccess) {
                        val data = profileData.getOrNull()
                        data?.let {
                            val imageBitmap = ImageUtils.base64ToBitmap(data.imageBase64)
                            _profileData.value = UiState.Success(
                                UserProfileUiModel(
                                    imageBitmap,
                                    data.username,
                                )
                            )
                        }
                    } else {
                        _profileData.value = UiState.Error(R.string.process_is_failure)
                    }
                }

            }
        }

    }


}