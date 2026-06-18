package com.example.ecommerceapp.repositories

import android.util.Log
import android.widget.Toast
import androidx.room.RoomDatabase
import com.example.ecommerceapp.model.Products
import com.example.ecommerceapp.roomdatabase.AppDatabase
import com.example.ecommerceapp.roomdatabase.CartDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepository @Inject constructor(private val cartDAO: CartDAO) {

    val allCartItems : Flow<List<Products>> = cartDAO.displayCartItems()

    suspend fun addToCart(products: Products){
        val existingProduct = cartDAO.getCartItemById(products.id)
        if(existingProduct !=null){
           Log.i("Malvika", "existing product")
            cartDAO.updateCartItem(products)
        }
        else{
            cartDAO.addToCart(products)
        }

    }

    suspend fun removeFromCart(products: Products){
        cartDAO.deleteItemFromCart(products)
    }

    suspend fun clearCart(){
        cartDAO.clearCart()
    }



}