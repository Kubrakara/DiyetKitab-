package com.example.diyetkitabi.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.diyetkitabi.model.Food

@Database(entities = [Food::class], version = 1)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDAO

    companion object {

        @Volatile
        private var instance: FoodDatabase? = null //singleton tek bir instanmce (nesne) oluşturuyor yoksa yeni oluşturuyor

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createDatabase(context).also {
                instance = it //boş olanı instanceyi databaseye eşitle
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            FoodDatabase::class.java,
            "FoodDatabase"
        ).build()

    }

}