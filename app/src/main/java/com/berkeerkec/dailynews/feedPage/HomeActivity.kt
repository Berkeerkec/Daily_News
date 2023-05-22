package com.berkeerkec.dailynews.feedPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.berkeerkec.dailynews.R
import com.berkeerkec.dailynews.databinding.ActivityHomeBinding
import com.berkeerkec.dailynews.hilt.NewsFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: NewsFragmentFactory

    private lateinit var binding : ActivityHomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)


        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.feedHomeFragment-> {
                     navController.navigate(R.id.feedFragment)
                    true
                }
                R.id.searchHomeFragment-> {
                    navController.navigate(R.id.searchFragment)
                    true
                }
                R.id.bookmarkedHomeFragment-> {
                    navController.navigate(R.id.bookMarkedFragment)
                    true
                }
                R.id.settingsHomeFragment-> {
                    navController.navigate(R.id.settingsFragment)
                    true
                }
                else -> false
            }
        }
    }

}