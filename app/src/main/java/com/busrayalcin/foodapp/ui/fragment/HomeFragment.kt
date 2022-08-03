package com.busrayalcin.foodapp.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.busrayalcin.foodapp.R
import com.busrayalcin.foodapp.databinding.FragmentHomeBinding
import com.busrayalcin.foodapp.ui.adapter.FoodAdapter
import com.busrayalcin.foodapp.ui.viewmodel.HomeFragmentViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setHasOptionsMenu(true)
        val tempViewModel: HomeFragmentViewModel by viewModels()
        viewModel = tempViewModel
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
            println("Test")
            println(it)
            val adapter = FoodAdapter(requireContext(),it,viewModel)
            binding.foodAdapter = adapter
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
        return true
    }

    override fun onResume() {
        super.onResume()
        viewModel.downloadFoods()
    }

}