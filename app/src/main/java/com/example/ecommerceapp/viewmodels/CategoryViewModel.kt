package com.example.ecommerceapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.model.CategoryClass
import com.example.ecommerceapp.repositories.FireStoreRepository
import com.example.ecommerceapp.screens.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoryViewModel
@Inject constructor(private val fireStoreRepository: FireStoreRepository)
    : ViewModel(){

        //encapsulation so that can be read only from repository
    private val _categories = MutableStateFlow<List<CategoryClass>>(emptyList())
    val categories : StateFlow<List<CategoryClass>>
        get() = _categories

    init {
        fetchCategories()

    }

    private fun fetchCategories(){
        viewModelScope.launch {
            fireStoreRepository.getCategoriesFlow()
                .catch {
                    println()
                }
                .collect {
                    categories ->
                    _categories.value = categories
                    println("categories update in vm ${_categories.value}")
                }
        }
    }


}