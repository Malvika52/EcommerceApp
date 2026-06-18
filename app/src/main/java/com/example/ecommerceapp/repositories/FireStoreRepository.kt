package com.example.ecommerceapp.repositories

import android.util.Log
import androidx.room.Query
import com.example.ecommerceapp.model.CategoryClass
import com.example.ecommerceapp.model.Products
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Provides
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FireStoreRepository @Inject constructor(private val firestore: FirebaseFirestore) {

    //callbackFlow  : lets you turn call back -based code into flow
    fun getCategoriesFlow() : Flow<List<CategoryClass>> = callbackFlow {

        val listenerRegistration = firestore
            .collection("categories")
            .addSnapshotListener { snapshot, error ->
                if(error!=null){
                    println("Error fetching categories :  ${error.message}")
                    return@addSnapshotListener
                }

                if(snapshot!=null){
                    val categories = snapshot.toObjects(CategoryClass::class.java)
                    Log.i("Malvika", "categories = $categories")
                    trySend(categories)
                }

            }

        awaitClose {
            listenerRegistration.remove()
        }

    }

    suspend fun getProductsByCategory(categoryId  : String) : List<Products> {

        return try {

            ///result will contain a querySnapshot of all the documents in products document
            //where "categoryId" matches the provided value
            val result = firestore.collection("products")
                .whereEqualTo("categoryId", categoryId)
                .get()
                .await()
            result.toObjects(Products::class.java).also {
                Log.v("Malvika", "products = $it")
            }
        }catch (e: Exception){
            emptyList()
        }
    }


    suspend fun getProductById(productId : String) : Products?{
        return try {

            ///result will contain a querySnapshot of all the documents in products document
            //where "categoryId" matches the provided value
            val result = firestore.collection("products")
                .document(productId)
                .get()
                .await()
            result.toObject(Products::class.java)
            }
        catch (e: Exception){
            null
        }
    }


    suspend fun getAllProductsInFirestore() : List<Products>{
        return try {
            ///result will contain a querySnapshot of all the documents in products document
            //where "categoryId" matches the provided value
            val result = firestore.collection("products")
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Products::class.java) }
            Log.i("Malvika", "all products = $result")
            result
        }
        catch (e: Exception){
            emptyList()
        }
    }


    suspend fun searchProducts(query: String) : List<Products>{
        return try{
            val searchQuery = query.lowercase()

            val result = firestore.collection("products")
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Products::class.java) }
            val actual = result.filter {
                product ->
                product.title.lowercase().contains(searchQuery)
            }
            Log.i("Malvika", "Actual results = $actual")
            return actual
        }
        catch (e: Exception){
            emptyList()
        }



    }
}


