package com.busrayalcin.foodapp.data.repo


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.busrayalcin.foodapp.data.entity.*
import com.busrayalcin.foodapp.retrofit.FoodDao
import com.busrayalcin.kisileruygulamasi.data.entity.CRUDResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class FoodDaoRepo @Inject constructor(var fdao : FoodDao) {
    var foodList : MutableLiveData<List<Food>>
    var cartFoodList : MutableLiveData<List<CartFood>>
    init {
        foodList = MutableLiveData()
        cartFoodList = MutableLiveData()

    }
    fun getFood() : MutableLiveData<List<Food>>{
        return foodList
    }

    fun getAllFoods() {
        println("getAllFoods")
        fdao.getAllFoods().enqueue(object : Callback<FoodResponse>{
            override fun onResponse(call: Call<FoodResponse>?, response: Response<FoodResponse>) {
                println("getAllFoods onResponse")
                val list = response.body()!!.foodList
                foodList.value = list
            }
            override fun onFailure(call: Call<FoodResponse>?, t: Throwable?) {
                println("getAllFoods onFailure")
                println("$t.localizedMessage")
            }
        })
    }

    fun foodAddCart(yemek_adi : String,yemek_resim_adi : String,yemek_fiyat : Int,yemek_siparis_adet : Int,kullanici_adi : String){
            fdao.foodAddCart(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi).enqueue(object : Callback<CRUDResponse>{
            override fun onResponse(call: Call<CRUDResponse>?, response: Response<CRUDResponse>) {
                val success = response.body()?.success
                val message = response.body()?.message
                Log.e("API Response", "Your order has been placed. $success - $message")
            }

            override fun onFailure(call: Call<CRUDResponse>?, t: Throwable?) {
                println("foodAddCart onFailure")
                println(t?.localizedMessage)
            }


        })
    }
    fun getCartFoods(): MutableLiveData<List<CartFood>> {
        return cartFoodList
    }

    fun getCartFood(kullanici_adi : String){
        fdao.getCartFoods(kullanici_adi).enqueue(object : Callback<CartFoodResponse>{
            override fun onResponse(
                call: Call<CartFoodResponse>?,
                response: Response<CartFoodResponse>
            ) {
                val list = response.body()?.sepet_yemekler
                cartFoodList.value = list!!
            }

            override fun onFailure(call: Call<CartFoodResponse>?, t: Throwable?) {
                cartFoodList.value = emptyList()
            }
        })
    }

    fun deleteCartFood(sepet_yemek_id : Int,kullanici_adi : String){
        fdao.deleteCartFood(sepet_yemek_id,kullanici_adi).enqueue(object : Callback<CRUDResponse>{
            override fun onResponse(call: Call<CRUDResponse>?, response: Response<CRUDResponse>) {
                val success = response.body()?.success
                val message = response.body()?.message
                Log.e("Delete Food", " $success - $message")
            }

            override fun onFailure(call: Call<CRUDResponse>?, t: Throwable?) {
                println("deleteCartFood onFailure")
                println(t?.localizedMessage)
            }

        })
    }
}