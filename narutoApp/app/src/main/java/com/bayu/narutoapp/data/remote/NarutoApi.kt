package com.bayu.narutoapp.data.remote

import com.bayu.narutoapp.domain.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NarutoApi {

    @GET("/naruto/heroes")
    suspend fun getAllHeroes(
        @Query("page") page: Int = 1,
    ): ApiResponse

    @GET("/naruto/heroes/search")
    suspend fun searchHeroes(
        @Query("name") name: String
    ): ApiResponse
}