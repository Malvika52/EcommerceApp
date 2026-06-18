package com.example.ecommerceapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.model.CategoryClass
import com.example.ecommerceapp.model.Products
import com.example.ecommerceapp.repositories.FireStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject
constructor(private val fireStoreRepository: FireStoreRepository) : ViewModel() {

    private val _productDetails = MutableStateFlow<Products?>(null)
    val productDetails : StateFlow<Products?>
        get() = _productDetails

    fun fetchProductsDetailsByProductId(productId : String){
        viewModelScope.launch {
            try{
                val details = fireStoreRepository.getProductById(productId)
                _productDetails.value = details
            }catch (e  : Exception){
                Log.v("TAGY", "Exception in the issue  : ${e.message}")
            }
        }

    }



}