package com.berkeerkec.dailynews.feedPage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.berkeerkec.dailynews.R
import com.berkeerkec.dailynews.adapters.NewsAdapter
import com.berkeerkec.dailynews.databinding.FragmentSearchBinding
import com.berkeerkec.dailynews.util.Constant
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
        binding.searchRecyclerView.addOnScrollListener(this@SearchFragment.scrollListener)

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
                    binding.progressBar2.visibility = View.GONE
                    isLoading = false
                    binding.searchRecyclerView.visibility = View.VISIBLE
                    response.data?.let {
                        adapter.differ.submitList(it.articles.toList())
                        val totalPages = it.totalResults / Constant.QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.searchNewsPage == totalPages
                        if (isLastPage){
                            binding.searchRecyclerView.setPadding(0,0,0,0)
                        }
                    }
                }
                is Resource.Error -> {
                    binding.progressBar2.visibility = View.GONE
                    isLoading = false
                    response.message?.let {
                        Toast.makeText(requireContext(),"An error occured: $it", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    binding.progressBar2.visibility = View.VISIBLE
                    binding.searchRecyclerView.visibility = View.GONE
                    isLoading = true
                }
            }
        })

        adapter.setOnItemClickListener {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailsFragment(it))
        }
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener(){

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constant.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling

            if (shouldPaginate){
                viewModel.getSearchNews(fragmentBinding?.searchTextView?.text.toString())
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }
    }


}