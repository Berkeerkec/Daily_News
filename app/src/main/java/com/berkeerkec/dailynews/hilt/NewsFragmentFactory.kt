package com.berkeerkec.dailynews.hilt

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.berkeerkec.dailynews.adapters.NewsAdapter
import com.berkeerkec.dailynews.feedPage.*
import com.berkeerkec.dailynews.loginPage.LoginFragment
import com.berkeerkec.dailynews.loginPage.SignUpFragment
import com.bumptech.glide.RequestManager
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class NewsFragmentFactory @Inject constructor(
    private val adapter : NewsAdapter,
    private val auth : FirebaseAuth
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){

            FeedFragment::class.java.name -> FeedFragment(adapter)
            SearchFragment::class.java.name -> SearchFragment(adapter)
            BookMarkedFragment::class.java.name -> BookMarkedFragment(adapter)
            LoginFragment::class.java.name -> LoginFragment(auth)
            SignUpFragment::class.java.name -> SignUpFragment(auth)
            SettingsFragment::class.java.name -> SettingsFragment(auth)
            else -> super.instantiate(classLoader, className)
        }
    }
}