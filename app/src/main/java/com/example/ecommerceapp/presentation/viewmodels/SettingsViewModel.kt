package com.example.ecommerceapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.R
import com.example.ecommerceapp.domain.usecases.settingsusecases.LogoutUseCase
import com.example.ecommerceapp.presentation.uimodels.SettingsModel
import com.example.ecommerceapp.presentation.uistates.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(val logoutUseCase: LogoutUseCase) : ViewModel() {

    private var _logout = MutableLiveData<UiState<Int>>()
    val logout: LiveData<UiState<Int>> get() = _logout

    private var _personalSettingsItem = MutableLiveData<ArrayList<Int>>()
    val personalSettingsItem: LiveData<ArrayList<Int>> get() = _personalSettingsItem

    private var _accountSettingsItem = MutableLiveData<ArrayList<SettingsModel>>()
    val accountSettingsItem: LiveData<ArrayList<SettingsModel>> get() = _accountSettingsItem

    init {
        getSettingsItem()
    }

    fun logout() {
        _logout.value = UiState.Loading
        viewModelScope.launch {
            var result = logoutUseCase()
            _logout.value = if (result.isSuccess) UiState.Success(R.string.successful_logout)
            else UiState.Error(R.string.failure_logout)
        }
    }

    fun getSettingsItem() {
        _personalSettingsItem.value = arrayListOf<Int>(
            R.string.profile,
            R.string.shipping_address,
            R.string.payment_methods
        )
        _accountSettingsItem.value = arrayListOf<SettingsModel>(
            SettingsModel(R.string.language, R.string.english),
            SettingsModel(R.string.about_slada, null)
        )


    }


}

