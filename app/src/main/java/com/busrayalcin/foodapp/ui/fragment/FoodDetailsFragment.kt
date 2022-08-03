package com.busrayalcin.foodapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.busrayalcin.foodapp.R
import com.busrayalcin.foodapp.databinding.FragmentFoodDetailsBinding
import com.busrayalcin.foodapp.databinding.FragmentHomeBinding


class FoodDetailsFragment : Fragment() {

    private lateinit var binding : FragmentFoodDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_food_details,container,false)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarFoodDetails)
        binding.toolbarFoodDetails.setLogo(R.drawable.logofoodies)

        return binding.root
    }

}