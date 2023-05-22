package com.berkeerkec.dailynews.feedPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import com.berkeerkec.dailynews.R
import com.berkeerkec.dailynews.databinding.FragmentDetailsBinding
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class DetailsFragment : Fragment() {

    private var fragmentBinding : FragmentDetailsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailsBinding.bind(view)
        fragmentBinding = binding

        arguments?.let {

            val article = DetailsFragmentArgs.fromBundle(it).article
            binding.deatilsWebView.apply {
                webViewClient = WebViewClient()
                loadUrl(article.url)
            }
        }

        binding.detailsBackView.setOnClickListener {
            findNavController().popBackStack()
        }

    }


}