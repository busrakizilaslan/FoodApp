package com.busrayalcin.foodapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.busrayalcin.foodapp.data.entity.CartFood
import com.busrayalcin.foodapp.data.entity.Food
import com.busrayalcin.foodapp.data.repo.FoodDaoRepo
import com.busrayalcin.foodapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    fun downloadFoods() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = frepo.getAllFoods()))
        } catch (exception: Exception) {
            emit(Resource.error(exception.message ?: "Error !!!", data = null))
        }
    }

    fun getCart(kullanici_adi : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = frepo.getCartFood(kullanici_adi)))
        } catch (exception: Exception) {
            emit(Resource.error(exception.message ?: "Error !!!", data = null))
        }
    }



}