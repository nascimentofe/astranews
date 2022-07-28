package com.fenascimento.astranews

import com.fenascimento.astranews.core.Query
import com.fenascimento.astranews.data.Post
import com.fenascimento.astranews.data.SpaceFlightNewsCategory
import com.fenascimento.astranews.domain.GetLatestPostsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GetLatestPostsUseCaseTest : KoinTest {

    private val getLatestPostsUseCase: GetLatestPostsUseCase by inject()

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
    fun deve_RetornarResultadoNaoNulo_AoConectarComRepositorio() {
        runBlocking {
            val result = getLatestPostsUseCase(Query(SpaceFlightNewsCategory.ARTICLES.value))

            assertNotNull(result)
        }
    }

    @Test
    fun deve_RetornarObjetoDoTipoCorretoAoConectarComRepositorio() {
        runBlocking {
            val result = getLatestPostsUseCase(Query(SpaceFlightNewsCategory.ARTICLES.value))

            assertTrue(result is Flow<List<Post>>)
        }
    }

    @Test
    fun deve_RetornarResultadoNaoVazioAoConectarComRepositorio() {
        runBlocking {
            val result = getLatestPostsUseCase(Query(SpaceFlightNewsCategory.ARTICLES.value))

            assertFalse(result.first().isEmpty())
        }
    }

}