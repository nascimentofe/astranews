package com.fenascimento.astranews.domain

import com.fenascimento.astranews.core.Query
import com.fenascimento.astranews.core.UseCase
import com.fenascimento.astranews.data.Post
import com.fenascimento.astranews.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetLatestPostsUseCase(private val repository: PostRepository) : UseCase<Query, List<Post>>() {

    override suspend fun execute(query: Query): Flow<List<Post>> = repository.listPosts(query.type)

}