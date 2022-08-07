package com.busrayalcin.foodapp.ui.viewmodel

import android.util.Log
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

    fun getCartTotal(): Double {
        var total = 0.00
        cartFoodList.value?.forEach {
            total += (it.yemek_fiyat * it.yemek_siparis_adet).toDouble()
        }
        return total
    }

    private fun getCartFoodID(): ArrayList<Int> {
        var idList  = ArrayList<Int>()
        cartFoodList.value?.forEach {
            idList.add(it.sepet_yemek_id)
        }
        return idList
    }

    fun deleteAllCartFood(kullanici_adi : String) {
        val idList = getCartFoodID()
        idList.forEach {
            Log.e("Cart ID: ", "$idList")
            frepo.deleteCartFood(it, kullanici_adi)
        }
    }
}