package com.berkeerkec.dailynews.feedPage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.berkeerkec.dailynews.R
import com.berkeerkec.dailynews.adapters.NewsAdapter
import com.berkeerkec.dailynews.databinding.FragmentSearchBinding
import com.berkeerkec.dailynews.util.Constant.Companion.SEARCH_NEWS_TIME_DELAY
import com.berkeerkec.dailynews.util.Resource
import com.berkeerkec.dailynews.viewmodel.SearchViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment @Inject constructor(
    private val adapter : NewsAdapter
): Fragment() {

    private var fragmentBinding : FragmentSearchBinding? = null
    lateinit var viewModel : SearchViewModel
    val TAG = "SearchFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSearchBinding.bind(view)
        fragmentBinding = binding

        viewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]

        binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchRecyclerView.adapter = adapter

        var job : Job? = null

        binding.searchTextView.addTextChangedListener {editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (it.toString().isNotEmpty()){
                        viewModel.getSearchNews(editable.toString())
                    }
                }
            }

        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer {response ->

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