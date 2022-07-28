package com.fenascimento.astranews.data

import com.fenascimento.astranews.data.services.SpaceFlightNewsService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class SpaceFligthNewsServiceTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var service: SpaceFlightNewsService

    @Before
    fun createService() {

        val factory = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .build()
            .create(SpaceFlightNewsService::class.java)

    }

    @After
    fun stopService() {

        mockWebServer.shutdown()

    }

    @Test
    fun deve_AlcancarOEndpointArticles_AoReceberParametro() {
        // devido a funcao listpost ser uma suspend fun, necessario o runBlocking
        runBlocking {
            mockWebServer.enqueue(MockResponse().setBody("[]"))
            val resultArticles = service.listPosts(SpaceFlightNewsCategory.ARTICLES.value)
            val requestArticles = mockWebServer.takeRequest()
            assertEquals(requestArticles.path, "/articles")
        }
    }


    @Test
    fun deve_AlcancarOEndpointBlogs_AoReceberParametro() {
        // devido a funcao listpost ser uma suspend fun, necessario o runBlocking
        runBlocking {
            // o retorno da chamada, neste caso, nao importa ja que estamos testando
            // somente a chamada com sucesso ao endpoint.
            mockWebServer.enqueue(MockResponse().setBody("[]"))
            val resultBlogs = service.listPosts(SpaceFlightNewsCategory.BLOGS.value)
            val requestBlogs = mockWebServer.takeRequest()
            assertEquals(requestBlogs.path, "/blogs")
        }
    }


    @Test
    fun deve_AlcancarOEndpointReports_AoReceberParametro() {
        runBlocking {
            mockWebServer.enqueue(MockResponse().setBody("[]"))
            val resultReports = service.listPosts(SpaceFlightNewsCategory.REPORTS.value)
            val requestReports = mockWebServer.takeRequest()
            assertEquals(requestReports.path, "/reports")
        }
    }

    @Test
    fun deve_AlcancarOEndpointArticles_AoReceberQueryComOption() {
        runBlocking {
            mockWebServer.enqueue(MockResponse().setBody("[]"))
            service.listPostsTitleContains(SpaceFlightNewsCategory.ARTICLES.value, "mars")
            val request = mockWebServer.takeRequest()
            println(request.path)
            assertEquals(request.path, "/articles?title_contains=mars")
        }
    }

    @Test
    fun deve_AlcancarOEndpointArticles_AoReceberQueryComOptionNull() {
        runBlocking {
            mockWebServer.enqueue(MockResponse().setBody("[]"))
            service.listPostsTitleContains(SpaceFlightNewsCategory.ARTICLES.value, null)
            val request = mockWebServer.takeRequest()
            println(request.path)
            assertEquals(request.path, "/articles")
        }
    }

}