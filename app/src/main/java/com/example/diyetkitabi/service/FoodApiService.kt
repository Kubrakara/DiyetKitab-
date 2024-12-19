package com.example.diyetkitabi.service


import com.example.diyetkitabi.model.Food
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FoodApiService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FoodApi::class.java)

    suspend fun getData(): List<Food> {
        return retrofit.getFoods()
    }

}