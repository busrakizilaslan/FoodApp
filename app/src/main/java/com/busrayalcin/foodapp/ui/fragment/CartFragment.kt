package com.busrayalcin.foodapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.busrayalcin.foodapp.R
import com.busrayalcin.foodapp.databinding.FragmentCartBinding
import com.busrayalcin.foodapp.ui.adapter.CartFoodAdapter
import com.busrayalcin.foodapp.ui.viewmodel.CartFragmentViewModel
import com.busrayalcin.foodapp.ui.viewmodel.FoodDetailsFragmentViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding : FragmentCartBinding
    private lateinit var viewModel: CartFragmentViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUserEmail : String
    private var cartTotalPrice : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val tempViewModel : CartFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart,container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarCart)
        binding.toolbarCart.setLogo(R.drawable.logofoodies)
        binding.toolbarCart.title = ""

        currentUserEmail = auth.currentUser?.email.toString()

        viewModel.cartFoodList.observe(viewLifecycleOwner){
            val adapter = CartFoodAdapter(requireContext(),it,viewModel)
            binding.cartFoodAdapter = adapter
            if (it.isEmpty()){
                binding.tvCartTotalPrice.visibility = View.INVISIBLE
                binding.buttonPlaceOrder.visibility = View.INVISIBLE
                binding.lottieEmptyCart.visibility = View.VISIBLE
            }else{
                binding.tvCartTotalPrice.visibility = View.VISIBLE
                binding.buttonPlaceOrder.visibility = View.VISIBLE
                binding.lottieEmptyCart.visibility = View.INVISIBLE
            }
        }

        for (food in viewModel.cartFoodList.value!!){
            println("food $food")
            cartTotalPrice += (food.yemek_fiyat * food.yemek_siparis_adet)
        }
        binding.tvCartTotalPrice.text = "Total Price : ${cartTotalPrice.toString()} ₺"


        backPress()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCart(currentUserEmail)
    }

    fun backPress(){
        binding.toolbarCart.setNavigationIcon(R.drawable.back_24)
        binding.toolbarCart.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

}