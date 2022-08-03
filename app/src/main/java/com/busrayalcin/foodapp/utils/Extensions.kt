package com.busrayalcin.foodapp.utils

import android.view.View
import android.widget.ImageView
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.squareup.picasso.Picasso

fun Navigation.doNavigate(v: View, id:Int){
    findNavController(v).navigate(id)
}

fun Navigation.doNavigate(v: View, id: NavDirections){
    findNavController(v).navigate(id)
}

fun ImageView.showUrlImage(pictureName : String) {
    var url = "http://kasimadalan.pe.hu/yemekler/resimler/$pictureName"
    Picasso.get().load(url).into(this)
}