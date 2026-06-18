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
class ProductViewModel @Inject constructor(
    private val fireStoreRepository: FireStoreRepository
) : ViewModel() {


    private val _products = MutableStateFlow<List<Products>>(emptyList())
    val products : StateFlow<List<Products>>
        get() = _products

    private val _allProducts = MutableStateFlow<List<Products>>(emptyList())
    val allProducts : StateFlow<List<Products>>
        get() = _allProducts

    init {
        fetchAllProductsInFirestore()
    }

    fun fetchProductsById(categoryId:  String){
        viewModelScope.launch {
            try{
                val products = fireStoreRepository.getProductsByCategory(categoryId)
                _products.value = products
            }catch (e : Exception){
                Log.v("TAGY", "Error fetching issue : ${e.message}")
            }

        }
    }


    fun fetchAllProductsInFirestore(){
       viewModelScope.launch {
           try{
               val products = fireStoreRepository.getAllProductsInFirestore()
               _allProducts.value = products

           }catch (e  :Exception){
               Log.v("TAGY", "Error fetching issue : ${e.message}")
           }
       }

    }


}