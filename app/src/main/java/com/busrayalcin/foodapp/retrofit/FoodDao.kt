package com.busrayalcin.foodapp.retrofit

import com.busrayalcin.foodapp.data.entity.FoodResponse
import retrofit2.Call
import retrofit2.http.GET

interface FoodDao {
    //http://kasimadalan.pe.hu/yemekler/tumYemekleriGetir.php
    @GET("yemekler/tumYemekleriGetir.php")
    fun getAllFoods() : Call<FoodResponse>
}