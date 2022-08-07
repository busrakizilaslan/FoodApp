package com.busrayalcin.foodapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.busrayalcin.foodapp.data.entity.Food
import com.busrayalcin.foodapp.data.repo.FoodDaoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoodDetailsFragmentViewModel @Inject constructor(var frepo : FoodDaoRepo) : ViewModel(){

    fun addCart(yemek_adi : String,
                yemek_resim_adi : String,
                yemek_fiyat : Int,
                yemek_siparis_adet : Int,
                kullanici_adi : String){
        frepo.foodAddCart(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
    }

    fun addFoodToList(food: Food) { ///SONRADAN EKLENECEK
        frepo.addCartFoodList.add(food)
    }
}