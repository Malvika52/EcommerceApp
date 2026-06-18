package com.example.ecommerceapp.roomdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.example.ecommerceapp.model.Products
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDAO {

    //avoid duplication and update existing items
    @Insert(onConflict = REPLACE)
    suspend fun addToCart(cartItem  : Products)

    @Update
    suspend fun updateCartItem(cartItem: Products)

    @Delete
    suspend fun deleteItemFromCart(cartItem: Products)

    @Query("SELECT * FROM cartsTable")
    fun displayCartItems() : Flow<List<Products>>

    @Query("SELECT * FROM cartsTable WHERE id = :productId")
    suspend fun getCartItemById(productId  : String) : Products?

    @Query("DELETE FROM cartsTable")
    suspend fun clearCart()
}