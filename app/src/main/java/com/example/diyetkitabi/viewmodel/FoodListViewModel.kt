package com.example.diyetkitabi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyetkitabi.model.Food
import com.example.diyetkitabi.roomdb.FoodDatabase
import com.example.diyetkitabi.service.FoodApiService
import com.example.diyetkitabi.util.PrivateSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodListViewModel(application: Application) : AndroidViewModel(application){  //AndroidViewModel sınıfından türetildi çünkü contexte ulaşmak istiyoruz

    val foods = MutableLiveData<List<Food>>()
    val foodErrorMessage = MutableLiveData<Boolean>()  //mesajları true false durumlarına göre gösterip gizleyeceğiz
    val foodLoading = MutableLiveData<Boolean>()

    private val foodApiService = FoodApiService()   //internete istek atıcaz besinApiservis  //besinleri listeliycez
    private val privateSharedPreferences = PrivateSharedPreferences(getApplication())

    private val updateTime = 0.1 * 60 * 1000 * 1000 * 1000L  //dakikanın nanotime karşılığı long olarak

    fun refreshData(){
        val savedTime = privateSharedPreferences.getTime()  //kaydedilme zamanı
        if (savedTime != null && savedTime != 0L && System.nanoTime() - savedTime < updateTime){
        //roomdan verileri alıcaz
            getDataFromRoom()
        }else {
            getDataFromInternet()
        }
    }

    fun refreshDataFromInternet(){
        getDataFromInternet()
    }

    private fun getDataFromRoom(){
        foodLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val foodList = FoodDatabase(getApplication()).foodDao().getAllFood()
            withContext(Dispatchers.Main){
                showFoods(foodList)
                Toast.makeText(getApplication(),"Besinleri Roomdan aldık",Toast.LENGTH_LONG).show()
            }
        }
    }

    //verleri İNTERNETTEN çekicez

    private fun getDataFromInternet(){
        foodLoading.value = true
        //getdata suspend bir fonksiyon viewmodel içinde kullanmamız için scope oluşturmamız lazım corotione
        viewModelScope.launch (Dispatchers.IO) {
            val foodList = foodApiService.getData()
            withContext(Dispatchers.Main){
                foodLoading.value = false
                savedRoom(foodList)
                Toast.makeText(getApplication(),"Besinleri İnternetten aldık",Toast.LENGTH_LONG).show()
            }
        }

    }

    //internetten çektik rooma kaydettik idlerini atadık şimdi göstericez zamanı kaydedicez

    private fun showFoods(foodList: List<Food>){
        foods.value = foodList
        foodErrorMessage.value = false
        foodLoading.value = false

    }

    private fun savedRoom(foodList: List<Food>){
        viewModelScope.launch {
            val dao = FoodDatabase(getApplication()).foodDao()
            dao.deleteAllFood()
            val uuidList = dao.insertAll(*foodList.toTypedArray())  //listeyi tek tek elemanlara ayırıyor gibi düşün
            var i = 0
            while (i < foodList.size){
                foodList[i].uuid = uuidList[i].toInt()
                i = i + 1
            }
            showFoods(foodList)

        }
        privateSharedPreferences.saveTime(System.nanoTime())
    }

}






















