package com.berkeerkec.dailynews.hilt

import android.content.Context
import androidx.room.Room
import com.berkeerkec.dailynews.R
import com.berkeerkec.dailynews.api.NewsApi
import com.berkeerkec.dailynews.db.ArticleDatabase
import com.berkeerkec.dailynews.repo.NewsRepository
import com.berkeerkec.dailynews.repo.SearchRepository
import com.berkeerkec.dailynews.util.Constant.Companion.BASE_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit() : NewsApi{

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(NewsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context : Context) = Room.databaseBuilder(context,ArticleDatabase::class.java,"NewsDatabase").build()

    @Singleton
    @Provides
    fun provideDao(database: ArticleDatabase) = database.getArticleDao()

    @Singleton
    @Provides
    fun provideGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().error(R.drawable.ic_launcher_background).placeholder(R.drawable.ic_launcher_background)
        )

    @Singleton
    @Provides
    fun provideNewsRepository(api : NewsApi) : NewsRepository{
        return NewsRepository(api)
    }

    @Singleton
    @Provides
    fun provideSearchRepository(api : NewsApi) : SearchRepository{
        return SearchRepository(api)
    }
}