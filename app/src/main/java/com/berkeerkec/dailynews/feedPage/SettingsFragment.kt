package com.berkeerkec.dailynews.feedPage

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.berkeerkec.dailynews.R
import com.berkeerkec.dailynews.databinding.FragmentSettingsBinding
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SettingsFragment @Inject constructor(
    private val auth : FirebaseAuth
): Fragment() {

    private var fragmentBinding : FragmentSettingsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSettingsBinding.bind(view)
        fragmentBinding = binding

        binding.settigsLogoutButton.setOnClickListener {

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Exit")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes") { dialog, which ->
                    auth.signOut()
                    findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToMainActivity())
                }
                .setNegativeButton("No") { dialog, which ->
                    return@setNegativeButton
                }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }


}