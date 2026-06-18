package com.example.ecommerceapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.model.CategoryClass
import com.example.ecommerceapp.model.Products
import com.example.ecommerceapp.repositories.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewmodel @Inject constructor(
    private val cartRepository: CartRepository)
    : ViewModel() {


    val cartItems = cartRepository.allCartItems

    fun addToCart(products: Products) {
        viewModelScope.launch {
            cartRepository.addToCart(products)
        }
    }

    fun removeFromCart(products: Products) {
        viewModelScope.launch {
            cartRepository.removeFromCart(products)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            cartRepository.clearCart()
        }
    }

    fun calculateTotal( items : List<Products>) : Double{
        return items.sumOf { it.price }
    }




}