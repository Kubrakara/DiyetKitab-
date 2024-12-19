package com.example.diyetkitabi.service

import com.example.diyetkitabi.model.Food
import retrofit2.http.GET


interface FoodApi {
    //api.google.com/createuser  - readusers

    //https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    //BASE URL - https://raw.githubusercontent.com/
    //ENDPOINT - atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    suspend fun getFoods(): List<Food>  //istediğin zaman duraklatılabilen //coroutines


}