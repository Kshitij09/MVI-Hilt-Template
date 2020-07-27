package com.kshitijpatil.hiltdemo.data.remote

import com.squareup.moshi.Json
import retrofit2.http.GET

data class BlogNetworkEntity(
    @Json(name = "pk") val id: Int,
    val title: String,
    val body: String,
    val image: String,
    val category: String
)

interface BlogService {
    @GET("blogs")
    suspend fun getBlogs(): List<BlogNetworkEntity>
}