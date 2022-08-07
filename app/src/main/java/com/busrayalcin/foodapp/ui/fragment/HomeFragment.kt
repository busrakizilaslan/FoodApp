package com.busrayalcin.foodapp.ui.fragment

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.busrayalcin.foodapp.R
import com.busrayalcin.foodapp.data.entity.Food
import com.busrayalcin.foodapp.databinding.FragmentHomeBinding
import com.busrayalcin.foodapp.ui.adapter.FoodAdapter
import com.busrayalcin.foodapp.ui.viewmodel.HomeFragmentViewModel
import com.busrayalcin.foodapp.utils.Status
import com.busrayalcin.foodapp.utils.doNavigate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUserEmail : String
    private  var foodOrderList : ArrayList<Food>? = null
    private var bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setHasOptionsMenu(true)
        val tempViewModel: HomeFragmentViewModel by viewModels()
        viewModel = tempViewModel
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        currentUserEmail = auth.currentUser?.email.toString()
        viewModel.getCart(currentUserEmail)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        binding.toolbarHomeTitle = ""

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarHome)
        binding.toolbarHome.setLogo(R.drawable.logofoodies)


        viewModel.foodList.observe(viewLifecycleOwner){
            println(it)
            val adapter = FoodAdapter(requireContext(), it,viewModel, currentUserEmail)
            binding.foodAdapter = adapter
        }
        getDataFromAPI()
        binding.fabCart.setOnClickListener {
            Navigation.doNavigate(it,HomeFragmentDirections.actionHomeFragmentToCartFragment())
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)
        val item = menu.findItem(R.id.action_search)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_signout){
            auth.signOut()
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        println("onQueryTextSubmit")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        println("onQueryTextChange")
        val filteredlist: ArrayList<Food> = ArrayList()
        for (item in viewModel.foodList.value!!) {
            if (item.yemek_adi.lowercase(Locale.ROOT).contains(newText!!))
                filteredlist.add(item)
        }
        val adapter = FoodAdapter(requireContext(), filteredlist,viewModel, currentUserEmail)
        binding.foodAdapter = adapter
        return true
    }

    override fun onResume() {
        super.onResume()
    }

    private fun getDataFromAPI() {
        viewModel.downloadFoods().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("STATUS", "SUCCESS")
                    binding.rvFood.visibility = View.VISIBLE
                    binding.lottieloading.visibility = View.GONE
                    binding.lottieerror.visibility = View.GONE
                }
                Status.LOADING -> {
                    Log.e("STATUS", "LOADING")
                    binding.rvFood.visibility = View.GONE
                    binding.lottieloading.visibility = View.VISIBLE
                    binding.lottieerror.visibility = View.GONE
                }
                Status.ERROR -> {
                    Log.e("STATUS", "ERROR")
                    binding.rvFood.visibility = View.GONE
                    binding.lottieloading.visibility = View.GONE
                    binding.lottieerror.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }




    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Uygulamadan Çıkmak İstediğine Emin misin?")
                    builder.setMessage("Gel biraz daha yemek bakalım :)")
                    builder.setPositiveButton("evet",
                        DialogInterface.OnClickListener { dialog, which ->
                            android.os.Process.killProcess(android.os.Process.myPid())
                        })
                    builder.setNegativeButton("HAYIR",
                        DialogInterface.OnClickListener { dialog, which ->
                    })
                    builder.show()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }



}