package com.berkeerkec.dailynews.viewmodel

import com.berkeerkec.dailynews.db.ArticleDao
import com.berkeerkec.dailynews.model.Article
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val dao : ArticleDao
) {

    suspend fun insertArticle(article: Article) = dao.upsert(article)
}