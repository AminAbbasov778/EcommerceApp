package com.example.ecommerceapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.R
import com.example.ecommerceapp.domain.usecases.settingsusecases.GetAccountSettingsItemsUseCase
import com.example.ecommerceapp.domain.usecases.settingsusecases.GetPersonalSettingsItemsUseCase
import com.example.ecommerceapp.domain.usecases.settingsusecases.LogoutUseCase
import com.example.ecommerceapp.presentation.uimodels.SettingsModel
import com.example.ecommerceapp.presentation.uistates.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(val logoutUseCase: LogoutUseCase,val getPersonalSettingsItemsUseCase: GetPersonalSettingsItemsUseCase,val getAccountSettingsItemsUseCase: GetAccountSettingsItemsUseCase) : ViewModel() {

    private var _logout = MutableLiveData<UiState<Int>>()
    val logout: LiveData<UiState<Int>> get() = _logout

    private var _personalSettingsItem = MutableLiveData<List<Int>>()
    val personalSettingsItem: LiveData<List<Int>> get() = _personalSettingsItem

    private var _accountSettingsItem = MutableLiveData<List<SettingsModel>>()
    val accountSettingsItem: LiveData<List<SettingsModel>> get() = _accountSettingsItem

    init {
        getPersonalSettingsItem()
        getAccountSettingsItems()
    }

    fun logout() {
        _logout.value = UiState.Loading
        viewModelScope.launch {
            var result = logoutUseCase()
            _logout.value = if (result.isSuccess) UiState.Success(R.string.successful_logout)
            else UiState.Error(R.string.failure_logout)
        }
    }

    fun getPersonalSettingsItem() {
        _personalSettingsItem.value = getPersonalSettingsItemsUseCase()

    }
    fun getAccountSettingsItems(){
        _accountSettingsItem.value = getAccountSettingsItemsUseCase()
    }


}

