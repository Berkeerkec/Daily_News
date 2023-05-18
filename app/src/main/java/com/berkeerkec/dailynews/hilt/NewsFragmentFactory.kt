package com.berkeerkec.dailynews.hilt

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.berkeerkec.dailynews.adapters.NewsAdapter
import com.berkeerkec.dailynews.feedPage.FeedFragment
import javax.inject.Inject

class NewsFragmentFactory @Inject constructor(
    private val adapter : NewsAdapter
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){

            FeedFragment::class.java.name -> FeedFragment(adapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}