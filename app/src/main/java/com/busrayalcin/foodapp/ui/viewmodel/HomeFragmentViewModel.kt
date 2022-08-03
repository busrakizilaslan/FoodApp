package com.busrayalcin.foodapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.busrayalcin.foodapp.data.entity.Food
import com.busrayalcin.foodapp.data.repo.FoodDaoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(var frepo : FoodDaoRepo)
    : ViewModel() {

    var foodList = MutableLiveData<List<Food>>()
    init {
        downloadFoods()
        foodList = frepo.getFood()
    }

    fun downloadFoods(){
        frepo.getAllFoods()
    }
}