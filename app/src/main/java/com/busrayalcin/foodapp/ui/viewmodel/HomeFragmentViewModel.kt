package com.busrayalcin.foodapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.busrayalcin.foodapp.data.entity.CartFood
import com.busrayalcin.foodapp.data.entity.Food
import com.busrayalcin.foodapp.data.repo.FoodDaoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(var frepo : FoodDaoRepo)
    : ViewModel() {

    var foodList = MutableLiveData<List<Food>>()
    var cartFoodList = MutableLiveData<List<CartFood>>()

    init {
        downloadFoods()
        foodList = frepo.getFood()
        cartFoodList = frepo.getCartFoods()

    }

    fun downloadFoods(){
        frepo.getAllFoods()
    }

    fun addCart(yemek_adi : String,
                yemek_resim_adi : String,
                yemek_fiyat : Int,
                yemek_siparis_adet : Int,
                kullanici_adi : String){
        frepo.foodAddCart(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
    }

    fun getCart(kullanici_adi : String){
        frepo.getCartFood(kullanici_adi)
    }
}