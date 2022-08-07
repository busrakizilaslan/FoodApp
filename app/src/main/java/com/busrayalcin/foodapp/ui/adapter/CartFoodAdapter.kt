package com.busrayalcin.foodapp.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.busrayalcin.foodapp.R
import com.busrayalcin.foodapp.data.entity.CartFood
import com.busrayalcin.foodapp.data.entity.Food
import com.busrayalcin.foodapp.databinding.CartFoodRowBinding
import com.busrayalcin.foodapp.databinding.FoodRowBinding
import com.busrayalcin.foodapp.ui.fragment.CartFragmentDirections
import com.busrayalcin.foodapp.ui.fragment.HomeFragmentDirections
import com.busrayalcin.foodapp.ui.viewmodel.CartFragmentViewModel
import com.busrayalcin.foodapp.utils.doNavigate
import com.busrayalcin.foodapp.utils.showUrlImage
import com.google.android.material.snackbar.Snackbar

class CartFoodAdapter(var mContext: Context,
                      var cartFoodList:List<CartFood>,
                      var viewModel: CartFragmentViewModel
)
    : RecyclerView.Adapter<CartFoodAdapter.FoodViewHolder>(){

    inner class FoodViewHolder(binding: CartFoodRowBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding : CartFoodRowBinding
        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding : CartFoodRowBinding = DataBindingUtil.inflate(layoutInflater, R.layout.cart_food_row,parent,false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = cartFoodList[position]
        val hb = holder.binding
        hb.cartFoodObject = food
        hb.tl = " ₺"
        hb.ivFoodImage.showUrlImage(food.yemek_resim_adi)


        hb.ivDeleteFood.setOnClickListener {
            val builder = AlertDialog.Builder(mContext)
            builder.setTitle("Sepetten Çıkarmak İstediğine Emin misin?")
            builder.setMessage("${food.yemek_adi} sepetten çıkarılacak.")
            builder.setPositiveButton("evet",DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(mContext,"${food.yemek_adi} sepetten çıkarıldı.",Toast.LENGTH_SHORT).show()
                viewModel.deleteFood(food.sepet_yemek_id,food.kullanici_adi)
                Log.e("Çıkarılan Yemek :", "${food.sepet_yemek_id} -  ${food.kullanici_adi}")
                viewModel.getCart(food.kullanici_adi)
            })
            builder.setNegativeButton("HAYIR",DialogInterface.OnClickListener { dialog, which ->
            })
            builder.show()

        }

    }

    override fun getItemCount(): Int {
        return cartFoodList.size
    }
}