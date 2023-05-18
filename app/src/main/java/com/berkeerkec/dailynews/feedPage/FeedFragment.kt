package com.berkeerkec.dailynews.feedPage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.berkeerkec.dailynews.R
import com.berkeerkec.dailynews.adapters.NewsAdapter
import com.berkeerkec.dailynews.databinding.FragmentFeedBinding
import com.berkeerkec.dailynews.util.Resource
import com.berkeerkec.dailynews.viewmodel.NewsViewModel
import javax.inject.Inject

class FeedFragment @Inject constructor(
    private val adapter : NewsAdapter
): Fragment() {

    private var fragmentBinding : FragmentFeedBinding? = null
    lateinit var viewModel : NewsViewModel
    val TAG = "FeedFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentFeedBinding.bind(view)
        fragmentBinding = binding

        viewModel = ViewModelProvider(requireActivity())[NewsViewModel::class.java]

        binding.feedRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.feedRecyclerView.adapter = adapter

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer {response ->

            when(response){
                is Resource.Success -> {
                    response.data?.let {
                        adapter.differ.submitList(it.articles)
                    }
                }
                is Resource.Error -> {
                    response.message?.let {
                        Log.e(TAG, "An error accured: $it")
                    }
                }
                is Resource.Loading -> {

                }
            }
        })
    }


}