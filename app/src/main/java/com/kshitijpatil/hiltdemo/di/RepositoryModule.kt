package com.kshitijpatil.hiltdemo.di

import com.kshitijpatil.hiltdemo.data.Repository
import com.kshitijpatil.hiltdemo.data.local.BlogDao
import com.kshitijpatil.hiltdemo.data.local.CacheMapper
import com.kshitijpatil.hiltdemo.data.remote.BlogService
import com.kshitijpatil.hiltdemo.data.remote.NetworkMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(
        blogDao: BlogDao,
        blogService: BlogService,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): Repository = Repository(blogDao, blogService, cacheMapper, networkMapper)
}
