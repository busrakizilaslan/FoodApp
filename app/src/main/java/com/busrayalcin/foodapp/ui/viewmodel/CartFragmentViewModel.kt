package com.busrayalcin.foodapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.busrayalcin.foodapp.data.entity.CartFood
import com.busrayalcin.foodapp.data.entity.Food
import com.busrayalcin.foodapp.data.repo.FoodDaoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartFragmentViewModel @Inject constructor(var frepo : FoodDaoRepo) : ViewModel() {
    var cartFoodList = MutableLiveData<List<CartFood>>()

    init {
        cartFoodList = frepo.getCartFoods()
    }

    fun getCart(kullanici_adi : String){
        frepo.getCartFood(kullanici_adi)
    }

    fun deleteFood(sepet_yemek_id : Int,kullanici_adi : String){
        frepo.deleteCartFood(sepet_yemek_id,kullanici_adi)
    }
}