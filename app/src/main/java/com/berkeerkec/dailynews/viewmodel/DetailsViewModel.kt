package com.berkeerkec.dailynews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berkeerkec.dailynews.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository : DetailsRepository
): ViewModel(){

    fun insertArticle(article: Article) = viewModelScope.launch {
        repository.insertArticle(article)
    }

}