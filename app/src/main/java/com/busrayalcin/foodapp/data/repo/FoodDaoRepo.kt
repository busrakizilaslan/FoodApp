package com.busrayalcin.foodapp.data.repo


import androidx.lifecycle.MutableLiveData
import com.busrayalcin.foodapp.data.entity.*
import com.busrayalcin.foodapp.retrofit.FoodDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class FoodDaoRepo @Inject constructor(var fdao : FoodDao) {
    var foodList : MutableLiveData<List<Food>>
    init {
        foodList = MutableLiveData()
    }
    fun getFood() : MutableLiveData<List<Food>>{
        return foodList
    }

    fun getAllFoods() {
        println("getAllFoods")
        fdao.getAllFoods().enqueue(object : Callback<FoodResponse>{
            override fun onResponse(call: Call<FoodResponse>?, response: Response<FoodResponse>) {
                println("getAllFoods onResponse")
                val list = response.body().foodList
                foodList.value = list
                println("*******************************************")
                println(response.body().foodList[0].yemek_resim_adi)
                println("*******************************************")
            }
            override fun onFailure(call: Call<FoodResponse>?, t: Throwable?) {
                println("getAllFoods onFailure")
                println("$t.localizedMessage")
            }
        })
    }
}