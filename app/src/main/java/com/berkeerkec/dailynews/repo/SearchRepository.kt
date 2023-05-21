package com.berkeerkec.dailynews.repo

import com.berkeerkec.dailynews.api.NewsApi
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val api : NewsApi
) {

    suspend fun getSearchNews(countryCode : String, pageNumber : Int) = api.searchForNews(countryCode,pageNumber)
}