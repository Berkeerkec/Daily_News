package com.berkeerkec.dailynews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berkeerkec.dailynews.model.Article
import com.berkeerkec.dailynews.repo.BookmarkedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkedViewModel @Inject constructor(
    private val repository : BookmarkedRepository
): ViewModel() {

    fun getArticle() = repository.getArticleData()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.deleteArticle(article)
    }

    fun insertArticle(article: Article) = viewModelScope.launch {
        repository.insertArticle(article)
    }
}