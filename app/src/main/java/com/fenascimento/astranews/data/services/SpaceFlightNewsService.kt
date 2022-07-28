package com.fenascimento.astranews.data.services

import com.fenascimento.astranews.data.Post
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Essa interface é responsavel pela comunicacao com a API web
 */
interface SpaceFlightNewsService {

    @GET("{type}")
    suspend fun listPosts(@Path("type") type : String) : List<Post>

    @GET("{type}")
    suspend fun listPostsTitleContains(
        @Path("type") type : String,
        @Query("title_contains") titleContains : String?) : List<Post>

}