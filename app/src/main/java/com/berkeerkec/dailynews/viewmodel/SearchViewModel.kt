package com.berkeerkec.dailynews.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berkeerkec.dailynews.model.NewsResponse
import com.berkeerkec.dailynews.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
import com.berkeerkec.dailynews.api.NewsApi
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository : NewsApi
) : ViewModel(){

    val searchNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1

    var searchNewsResponse : NewsResponse? = null
    fun getSearchNews(countryCode : String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = searchRepository.searchForNews(countryCode, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleSearchNewsResponse(response : Response<NewsResponse>) : Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let {
                searchNewsPage++
                if (searchNewsResponse == null){
                    searchNewsResponse = it
                }else{
                    val oldArticle = searchNewsResponse?.articles
                    val newArticle = it.articles
                    oldArticle?.addAll(newArticle)
                }
                return Resource.Success(searchNewsResponse ?: it)
            }
        }
        return Resource.Error(response.message())
    }
}