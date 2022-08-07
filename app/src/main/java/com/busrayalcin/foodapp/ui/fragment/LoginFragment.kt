package com.busrayalcin.foodapp.ui.fragment

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.BaseInputConnection
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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


        binding.tiEmailAddress.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) {
                /////emulate
                Toast.makeText(context, "asd", Toast.LENGTH_SHORT).show();
                Log.e("enter", "asdsadsad")
                return@OnKeyListener true;
            }
            return@OnKeyListener false;
        })
        return binding.root

    }

    private fun signUp(){
        binding.buttonSignUp.setOnClickListener {
            val view = it
            val email = binding.tiEmailAddress.editText?.text.toString()
            val password = binding.tiPassword.editText?.text.toString()
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(requireContext(),"E-mail ve şifre boş olamaz!",Toast.LENGTH_SHORT).show()
            }else{
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Hesabınız oluşturuldu.",
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

    private fun signIn(){
        binding.buttonSignIn.setOnClickListener {
            val view = it
            val email = binding.tiEmailAddress.editText?.text.toString()
            val password = binding.tiPassword.editText?.text.toString()
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(requireContext(),"E-mail ve şifre boş olamaz!",Toast.LENGTH_SHORT).show()
            }else{
                auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Giriş Başarılı.",
                            Toast.LENGTH_SHORT).show()
                        Navigation.doNavigate(view,LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_LONG).show()
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
                    builder.setMessage("Biraz daha yemeklere bakmaya ne dersin?")
                    builder.setPositiveButton("evet",
                        DialogInterface.OnClickListener { dialog, which ->
                            android.os.Process.killProcess(android.os.Process.myPid())
                    })
                    builder.setNegativeButton("HAYIR",DialogInterface.OnClickListener { dialog, which ->
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