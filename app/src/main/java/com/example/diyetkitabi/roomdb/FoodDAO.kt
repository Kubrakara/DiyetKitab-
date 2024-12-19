package com.example.diyetkitabi.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.diyetkitabi.model.Food

@Dao
interface FoodDAO {

    @Insert
    suspend fun insertAll(vararg food : Food) : List<Long>  //hepsini birden fazla besini al  - eklediği besinlerin id sini long olarak geri veriyor

    @Query("SELECT * FROM Food")
    suspend fun getAllFood() : List<Food>

    @Query("SELECT * FROM Food WHERE uuid = :foodId")
    suspend fun getFood(foodId : Int) : Food

    @Query("DELETE FROM Food")
    suspend fun deleteAllFood()

}