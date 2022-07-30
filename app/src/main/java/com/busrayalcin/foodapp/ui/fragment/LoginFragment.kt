package com.busrayalcin.foodapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.busrayalcin.foodapp.R
import com.busrayalcin.foodapp.databinding.FragmentLoginBinding
import com.busrayalcin.foodapp.utils.doNavigate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding : FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)

        val currentUser = auth.currentUser
        if(currentUser != null){
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
        }

        signIn()
        signUp()

        return binding.root

    }

    fun signUp(){
        binding.buttonSignUp.setOnClickListener {
            val view = it
            val email = binding.tiEmailAddress.editText?.text.toString()
            val password = binding.tiPassword.editText?.text.toString()
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(requireContext(),"E-mail and password can not be empty! ",Toast.LENGTH_SHORT).show()
            }else{
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Your account has been created.",
                            Toast.LENGTH_SHORT).show()
                        Navigation.doNavigate(view,LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), it.localizedMessage,
                            Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    fun signIn(){
        binding.buttonSignIn.setOnClickListener {
            val view = it
            val email = binding.tiEmailAddress.editText?.text.toString()
            val password = binding.tiPassword.editText?.text.toString()
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(requireContext(),"E-mail and password can not be empty! ",Toast.LENGTH_SHORT).show()
            }else{
                auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Sign in is successful.",
                            Toast.LENGTH_SHORT).show()
                        Navigation.doNavigate(view,LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_LONG).show()
                    }
            }
        }

    }

}