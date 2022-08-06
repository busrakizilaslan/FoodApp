package com.busrayalcin.foodapp.retrofit

import com.busrayalcin.foodapp.data.entity.CartFoodResponse
import com.busrayalcin.foodapp.data.entity.FoodResponse
import com.busrayalcin.kisileruygulamasi.data.entity.CRUDResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodDao {
    //http://kasimadalan.pe.hu/yemekler/tumYemekleriGetir.php
    @GET("yemekler/tumYemekleriGetir.php")
    fun getAllFoods() : Call<FoodResponse>

    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    fun foodAddCart(@Field ("yemek_adi") yemek_adi : String,
                    @Field ("yemek_resim_adi") yemek_resim_adi : String,
                    @Field ("yemek_fiyat") yemek_fiyat : Int,
                    @Field ("yemek_siparis_adet") yemek_siparis_adet : Int,
                    @Field ("kullanici_adi") kullanici_adi : String) : Call<CRUDResponse>

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    fun getCartFoods(@Field ("kullanici_adi") kullanici_adi : String) : Call<CartFoodResponse>

    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    fun deleteCartFood(@Field ("sepet_yemek_id") sepet_yemek_id : Int,
                       @Field ("kullanici_adi") kullanici_adi : String) : Call<CRUDResponse>




}