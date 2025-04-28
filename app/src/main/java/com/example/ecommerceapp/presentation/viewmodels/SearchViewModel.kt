package com.example.ecommerceapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel  : ViewModel() {




    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> get()  = _searchQuery

    fun sendQuery(query: String) {
        val trimmedQuery = query.trim()
        if (trimmedQuery.isNotEmpty()) {
            _searchQuery.value = trimmedQuery
        }
    }
    fun clearData(){
        _searchQuery.value = ""
    }
}