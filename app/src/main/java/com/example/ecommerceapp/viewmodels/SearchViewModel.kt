package com.example.ecommerceapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.ecommerceapp.model.Products
import com.example.ecommerceapp.repositories.FireStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject constructor(private val fireStoreRepository: FireStoreRepository) : ViewModel() {


    private val _searchState = MutableStateFlow<List<Products>>(emptyList())
    val searchResult: MutableStateFlow<List<Products>>
        get() = _searchState

    private val _isSearching = MutableStateFlow(false)
    val isSearching: MutableStateFlow<Boolean>
        get() = _isSearching


    fun searchProducts(query: String) {
        if (query.isBlank()) {
            _searchState.value = emptyList()
            _isSearching.value = false
            return
        }
        _isSearching.value = true
        viewModelScope.launch {
            _searchState.value = fireStoreRepository.searchProducts(query)
            _isSearching.value = false
        }
    }
}