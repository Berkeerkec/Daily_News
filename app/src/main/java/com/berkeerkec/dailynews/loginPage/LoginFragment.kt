package com.berkeerkec.dailynews.loginPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.berkeerkec.dailynews.R
import com.berkeerkec.dailynews.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class LoginFragment @Inject constructor(
    private val auth: FirebaseAuth
): Fragment() {

    private var fragmentBinding : FragmentLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoginBinding.bind(view)
        fragmentBinding = binding

        binding.registerButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
        }
        binding.loginButton.setOnClickListener {

            val email = binding.loginEmailText.text.toString()
            val password = binding.loginPasswordText.text.toString()
            if (email.equals("") || password.equals("")){
                Toast.makeText(requireContext(),"Email and password enter", Toast.LENGTH_LONG).show()
            }else{
                auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeActivity())
                }.addOnFailureListener {
                    Toast.makeText(requireContext(),it.localizedMessage ?: "Error!", Toast.LENGTH_LONG).show()
                }
            }



        }
    }

}