package com.fenascimento.astranews.data.repository

import com.fenascimento.astranews.data.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun listPosts(category : String) : Flow<List<Post>>

    suspend fun listPostTitleContains(category: String, titleContains: String?) : Flow<List<Post>>

}