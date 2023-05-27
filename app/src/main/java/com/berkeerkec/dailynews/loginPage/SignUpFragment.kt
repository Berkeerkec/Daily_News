package com.berkeerkec.dailynews.loginPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.berkeerkec.dailynews.R
import com.berkeerkec.dailynews.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SignUpFragment @Inject constructor(
    private val auth: FirebaseAuth
): Fragment() {

    private var fragmentBinding : FragmentSignUpBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSignUpBinding.bind(view)
        fragmentBinding = binding

        binding.signUpButton.setOnClickListener {
            val email = binding.signUpEmailText.text.toString()
            val password = binding.signUpPasswordText.text.toString()

            if (email.equals("") || password.equals("")){
                Toast.makeText(requireContext(),"Email and password enter", Toast.LENGTH_LONG).show()
            }else{
                auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                    findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToHomeActivity())
                }.addOnFailureListener {
                    Toast.makeText(requireContext(),it.localizedMessage ?: "Error!", Toast.LENGTH_LONG).show()
                }
            }



        }

    }

}