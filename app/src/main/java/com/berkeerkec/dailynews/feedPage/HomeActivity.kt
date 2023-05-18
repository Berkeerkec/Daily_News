package com.berkeerkec.dailynews.feedPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.berkeerkec.dailynews.R
import com.berkeerkec.dailynews.adapters.NewsAdapter
import com.berkeerkec.dailynews.databinding.ActivityHomeBinding
import com.berkeerkec.dailynews.hilt.NewsFragmentFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: NewsFragmentFactory

    private lateinit var binding : ActivityHomeBinding
    private lateinit var adapter : NewsAdapter
    private lateinit var glide : RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        glide = Glide.with(applicationContext)
        adapter = NewsAdapter(glide)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.feedHomeFragment-> {
                     val feedHomeFragment = FeedFragment(adapter)
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