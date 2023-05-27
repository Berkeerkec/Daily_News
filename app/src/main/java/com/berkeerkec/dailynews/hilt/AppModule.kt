package com.berkeerkec.dailynews.hilt

import android.content.Context
import androidx.room.Room
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.berkeerkec.dailynews.R
import com.berkeerkec.dailynews.api.NewsApi
import com.berkeerkec.dailynews.db.ArticleDao
import com.berkeerkec.dailynews.db.ArticleDatabase
import com.berkeerkec.dailynews.repo.*
import com.berkeerkec.dailynews.util.Constant.Companion.BASE_URL
import com.berkeerkec.dailynews.viewmodel.DetailsRepository
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
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
            RequestOptions().error(R.drawable.error_image).placeholder(CircularProgressDrawable(context).apply {
                strokeWidth =  8f
                centerRadius = 40f
                start()
            })
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

    @Singleton
    @Provides
    fun provideDetailsRepository(dao : ArticleDao) : DetailsRepository {
        return DetailsRepository(dao)
    }

    @Singleton
    @Provides
    fun provideBookmarkedRepository(dao : ArticleDao) : BookmarkedRepository{
        return BookmarkedRepository(dao)
    }

    @Singleton
    @Provides
    fun provideFirebaseAuth() : FirebaseAuth = FirebaseAuth.getInstance()

}