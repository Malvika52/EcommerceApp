package com.example.ecommerceapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartsTable")
data class Products(
    val categoryId : String ="",
    @PrimaryKey val id : String = "",
    val imageUrl : String ="",
    val price : Double = 0.0,
    val title : String =""
)
