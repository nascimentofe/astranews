package com.fenascimento.astranews

import com.fenascimento.astranews.core.Query
import com.fenascimento.astranews.data.SpaceFlightNewsCategory
import com.fenascimento.astranews.domain.GetLatestPostsTitleContainsUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertTrue

class GetLatestPostsTitleContainsUseCaseTest : KoinTest {

    val getLatestPostsTitleContainsUseCase: GetLatestPostsTitleContainsUseCase by inject()

    private val type = SpaceFlightNewsCategory.ARTICLES.value
    private val searchString = "Mars"

    companion object {

        @BeforeClass
        @JvmStatic
        fun setUp() {
            configureTestAppComponent()
        }

        @AfterClass
        fun tearDown() {
            stopKoin()
        }
    }

    @Test
    fun deve_RetornarResultadosValidosAoExecutarBusca() {
        runBlocking {
            val result = getLatestPostsTitleContainsUseCase(Query(type, searchString))
            var assertion = true
            result.first().forEach { post ->
                println(post.title)
                assertion = assertion && post.title.contains(searchString)
            }
            assertTrue(assertion)
        }
    }

}