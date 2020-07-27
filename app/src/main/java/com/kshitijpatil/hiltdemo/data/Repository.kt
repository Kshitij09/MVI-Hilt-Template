package com.kshitijpatil.hiltdemo.data

import com.kshitijpatil.hiltdemo.data.local.BlogCacheEntity
import com.kshitijpatil.hiltdemo.data.local.BlogDao
import com.kshitijpatil.hiltdemo.data.local.CacheMapper
import com.kshitijpatil.hiltdemo.data.model.Blog
import com.kshitijpatil.hiltdemo.data.remote.BlogService
import com.kshitijpatil.hiltdemo.data.remote.NetworkMapper
import com.kshitijpatil.hiltdemo.utils.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository(
    private val blogDao: BlogDao,
    private val blogService: BlogService,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
) {
    suspend fun getBlogs(): Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val networkBlogs = blogService.getBlogs()
            val blogs: List<BlogCacheEntity> = networkMapper
                .mapFromEntityList(networkBlogs)
                .map(cacheMapper::mapToEntity)
            blogDao.insertAll(blogs)
            val cachedBlogs = blogDao.getBlogs()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedBlogs)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}