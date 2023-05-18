package com.berkeerkec.dailynews.loginPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.berkeerkec.dailynews.R
import com.berkeerkec.dailynews.hilt.NewsFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: NewsFragmentFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.activity_main)
    }
}