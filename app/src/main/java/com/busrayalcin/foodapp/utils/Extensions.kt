package com.busrayalcin.foodapp.utils

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

fun Navigation.doNavigate(v: View, id:Int){
    findNavController(v).navigate(id)
}

fun Navigation.doNavigate(v: View, id: NavDirections){
    findNavController(v).navigate(id)
}