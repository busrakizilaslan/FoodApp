package com.busrayalcin.foodapp.ui.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.busrayalcin.foodapp.R
import com.busrayalcin.foodapp.data.entity.Food
import com.busrayalcin.foodapp.databinding.FoodRowBinding
import com.busrayalcin.foodapp.ui.fragment.HomeFragmentDirections
import com.busrayalcin.foodapp.ui.viewmodel.HomeFragmentViewModel
import com.busrayalcin.foodapp.utils.doNavigate
import com.busrayalcin.foodapp.utils.showUrlImage


class FoodAdapter(
    var mContext: Context,
    var foodList:List<Food>,
    var viewModel: HomeFragmentViewModel,
    var currentUser: String
)
    : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>(){
private lateinit var foodOrderList : ArrayList<Food>

    inner class FoodViewHolder(binding: FoodRowBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding : FoodRowBinding
        init {
            this.binding = binding
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding : FoodRowBinding = DataBindingUtil.inflate(layoutInflater, R.layout.food_row,parent,false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodList[position]
        val hb = holder.binding
        hb.foodObject = food
        hb.ivFoodImage.showUrlImage(food.yemek_resim_adi)

        hb.cvFood.setOnClickListener {
            Navigation.doNavigate(it,HomeFragmentDirections.actionHomeFragmentToFoodDetailsFragment(food = food))
        }

        hb.ivCountDown.setOnClickListener {
            if (food.yemek_adet > 0){
                food.yemek_adet--
            }
            println("${food.yemek_adet} ---")
                foodOrderList[position] = food

//            hb.tvFoodCount.text = food.yemek_adet.toString()
//            hb.tvFoodTotalPrice.text = (food.yemek_fiyat * food.yemek_adet).toString().plus(" ₺")
        }

        hb.ivCountUp.setOnClickListener {
            // arttır -view model
            food.yemek_adet++
            println("${food.yemek_adet} +++ ")
            foodOrderList[position] = food
//            hb.tvFoodCount.text = food.yemek_adet.toString()
//            hb.tvFoodTotalPrice.text = (food.yemek_fiyat * food.yemek_adet).toString().plus(" ₺")
            //viewModel.addCart(food.yemek_adi,food.yemek_resim_adi,food.yemek_fiyat,food.yemek_adet,currentUser)
        }
    }


    override fun getItemCount(): Int {
        return foodList.size
    }
}