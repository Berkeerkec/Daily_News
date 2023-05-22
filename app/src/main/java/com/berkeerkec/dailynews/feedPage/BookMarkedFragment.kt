package com.berkeerkec.dailynews.feedPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.berkeerkec.dailynews.R
import com.berkeerkec.dailynews.adapters.NewsAdapter
import com.berkeerkec.dailynews.databinding.FragmentBookMarkedBinding
import com.berkeerkec.dailynews.viewmodel.BookmarkedViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class BookMarkedFragment @Inject constructor(
    private val adapter : NewsAdapter
): Fragment() {

    private var fragmentBinding : FragmentBookMarkedBinding? = null
    lateinit var viewModel : BookmarkedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_marked, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentBookMarkedBinding.bind(view)
        fragmentBinding = binding

        viewModel = ViewModelProvider(requireActivity())[BookmarkedViewModel::class.java]

        binding.bookmarkedRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.bookmarkedRecyclerView.adapter = adapter

        val callback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = adapter.differ.currentList[position]
                viewModel.deleteArticle(article)

                Snackbar.make(view,"Successfully deleted news", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.insertArticle(article)
                    }
                    show()
                }
            }

        }

        ItemTouchHelper(callback).attachToRecyclerView(binding.bookmarkedRecyclerView)

        viewModel.getArticle().observe(viewLifecycleOwner, Observer {
            adapter.differ.submitList(it)
        })

    }


}