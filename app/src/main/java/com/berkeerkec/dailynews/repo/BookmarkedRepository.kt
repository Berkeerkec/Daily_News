package com.berkeerkec.dailynews.repo

import com.berkeerkec.dailynews.db.ArticleDao
import com.berkeerkec.dailynews.model.Article
import javax.inject.Inject

class BookmarkedRepository @Inject constructor(
    private val dao : ArticleDao
) {

    fun getArticleData() = dao.getAllArticles()

    suspend fun deleteArticle(artical : Article) = dao.deleteArticle(artical)

    suspend fun insertArticle(article: Article) = dao.upsert(article)
}