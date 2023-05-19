package com.berkeerkec.dailynews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.berkeerkec.dailynews.databinding.DetailsRowBinding
import com.berkeerkec.dailynews.model.Article
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class DetailsAdapter @Inject constructor(
    private val glide : RequestManager
): RecyclerView.Adapter<DetailsAdapter.DetailsHolder>() {

    class DetailsHolder(val binding : DetailsRowBinding) : ViewHolder(binding.root)

    private val callback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsHolder {
        val binding = DetailsRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DetailsHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: DetailsHolder, position: Int) {
        val article = differ.currentList[position]
        holder.binding.detailsDescriptionView.text = article.content
        holder.binding.detailsTimeView.text = article.publishedAt
        holder.binding.detailsSourceView.text = article.source.name
        holder.binding.detailsTitleView.text = article.title
        glide.load(article.urlToImage).into(holder.binding.detailsImageView)
    }
}