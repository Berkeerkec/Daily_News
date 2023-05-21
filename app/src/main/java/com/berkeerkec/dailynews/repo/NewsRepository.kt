package com.berkeerkec.dailynews.repo

import com.berkeerkec.dailynews.api.NewsApi
import com.berkeerkec.dailynews.db.ArticleDao
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val api : NewsApi
) {
    suspend fun getBreakingNews(countryCode : String, pageNumber : Int) = api.getBreakingNews(countryCode,pageNumber)
}