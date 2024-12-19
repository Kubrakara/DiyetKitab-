package com.example.diyetkitabi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.diyetkitabi.model.Food
import com.example.diyetkitabi.roomdb.FoodDatabase
import kotlinx.coroutines.launch

class FoodDetailViewModel(application: Application) : AndroidViewModel(application) {

    val foodLiveData = MutableLiveData<Food>()

    fun getRoomData(uuid: Int) {
        viewModelScope.launch {
            val dao = FoodDatabase(getApplication()).foodDao()
            val food = dao.getFood(uuid)
            foodLiveData.value = food

        }
    }
}