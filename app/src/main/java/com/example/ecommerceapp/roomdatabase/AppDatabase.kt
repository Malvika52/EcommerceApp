package com.example.ecommerceapp.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ecommerceapp.model.Products
import dagger.hilt.android.qualifiers.ApplicationContext

@Database(entities = [Products::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {


    //define daos here
    abstract fun cartDao() :  CartDAO


    //Singleton instance for AppDatabase
    companion object{

        @Volatile // ensures visibility of changes across threads
        private var INSTANCE : AppDatabase? = null


        //synchronized: only one thread at a time can enter this block
        fun getDatabase(context: Context) : AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass =AppDatabase::class.java,
                    name = "cart_database"
                ).build()

                INSTANCE = instance
                instance

            }
        }

    }

}