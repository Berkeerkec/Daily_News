package com.berkeerkec.dailynews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.berkeerkec.dailynews.databinding.FeedNewsBinding
import com.berkeerkec.dailynews.model.Article
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class NewsAdapter @Inject constructor(
    private val glide : RequestManager
) : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    class NewsHolder(val binding : FeedNewsBinding) : ViewHolder(binding.root)

    private val callback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val binding = FeedNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NewsHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val article = differ.currentList[position]
        holder.binding.feedSourceText.text = article.source.name
        holder.binding.feedTimeText.text = article.publishedAt
        holder.binding.feedTitleText.text = article.title
        glide.load(article.urlToImage).into(holder.binding.feedView)
        holder.itemView.setOnClickListener {view ->
            onItemClickListener?.let {
                it(article)
            }
        }

    }

    private var onItemClickListener : ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener : (Article) -> Unit){
        onItemClickListener = listener
    }
}