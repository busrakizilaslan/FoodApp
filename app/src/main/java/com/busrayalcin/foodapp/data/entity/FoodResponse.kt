package com.busrayalcin.foodapp.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FoodResponse(@SerializedName("yemekler") var foodList : List<Food>,
                        @SerializedName("success") var success : Int) : Serializable {
}