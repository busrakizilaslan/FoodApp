package com.busrayalcin.foodapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.busrayalcin.foodapp.R
import com.busrayalcin.foodapp.data.entity.Food
import com.busrayalcin.foodapp.databinding.FoodRowBinding
import com.busrayalcin.foodapp.ui.viewmodel.HomeViewModel

class FoodAdapter(var mContext: Context,
                  var foodList:List<Food>,
                  var viewModel: HomeViewModel)
    : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>(){

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

        hb.cvFood.setOnClickListener {
            // detay sayfasına gidilecek
        }

        hb.ivCountDown.setOnClickListener {
            // azalt - view model

        }

        hb.ivCountUp.setOnClickListener {
            // arttır -view model
        }

    }

    override fun getItemCount(): Int {
        return foodList.size
    }
}