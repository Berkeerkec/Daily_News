package com.berkeerkec.dailynews.hilt

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.berkeerkec.dailynews.adapters.DetailsAdapter
import com.berkeerkec.dailynews.adapters.NewsAdapter
import com.berkeerkec.dailynews.feedPage.DetailsFragment
import com.berkeerkec.dailynews.feedPage.FeedFragment
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class NewsFragmentFactory @Inject constructor(
    private val adapter : NewsAdapter,
    private val glide : RequestManager,
    private val detailsAdapter : DetailsAdapter
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){

            FeedFragment::class.java.name -> FeedFragment(adapter)
            DetailsFragment::class.java.name -> DetailsFragment(glide, detailsAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}