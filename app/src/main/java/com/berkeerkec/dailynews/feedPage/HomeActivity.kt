package com.berkeerkec.dailynews.feedPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.berkeerkec.dailynews.R
import com.berkeerkec.dailynews.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.feedHomeFragment-> {
                     val feedHomeFragment = FeedFragment()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fragmentContainerView2,
                        feedHomeFragment
                    ).commit()
                    true
                }
                R.id.searchHomeFragment-> {
                    val searchHomeFragment = SearchFragment()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fragmentContainerView2,
                        searchHomeFragment
                    ).commit()
                    true
                }
                R.id.bookmarkedHomeFragment-> {
                    val bookmarkedHomeFragment = BookMarkedFragment()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fragmentContainerView2,
                        bookmarkedHomeFragment
                    ).commit()
                    true
                }
                R.id.settingsHomeFragment-> {
                    val settingsHomeFragment = SettingsFragment()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fragmentContainerView2,
                        settingsHomeFragment
                    ).commit()
                    true
                }
                else -> false
            }
        }
    }
}