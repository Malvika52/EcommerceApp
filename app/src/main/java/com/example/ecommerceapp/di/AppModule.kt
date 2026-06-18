package com.example.ecommerceapp.di

import android.content.Context
import com.example.ecommerceapp.repositories.CartRepository
import com.example.ecommerceapp.repositories.FireStoreRepository
import com.example.ecommerceapp.roomdatabase.AppDatabase
import com.example.ecommerceapp.roomdatabase.CartDAO
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore()  :  FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase{
        return AppDatabase.getDatabase(context)

    }

    @Provides
    @Singleton
    fun provideCartDao(appDatabase: AppDatabase) : CartDAO{
        return appDatabase.cartDao()
    }

    @Provides
    @Singleton
    fun provideCartRepository(cartDAO: CartDAO) : CartRepository{
        return CartRepository(cartDAO)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth() : FirebaseAuth{
        return Firebase.auth
    }

}