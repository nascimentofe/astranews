package com.fenascimento.astranews.data.di

import android.util.Log
import com.fenascimento.astranews.data.repository.PostRepository
import com.fenascimento.astranews.data.repository.PostRepositoryImpl
import com.fenascimento.astranews.data.services.SpaceFlightNewsService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


/**
 * Osse objeto organiza as pendecias da camada Data.
 */
object DataModule {

    private const val BASE_URL = "https://api.spaceflightnewsapi.net/v3/"
    private const val OK_HTTP = "Ok Http"

    fun load() {
        loadKoinModules(postModule() + networkModule())
    }

    private inline fun <reified T> createService(factory: Moshi, client: OkHttpClient) : T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .client(client)
            .build()
            .create(T::class.java)
    }

    /**
     * MODULES
     */

    private fun postModule() : Module {
        return module {
            single<PostRepository> { PostRepositoryImpl(get()) }
        }
    }

    private fun networkModule() : Module {
        return module {
            single<SpaceFlightNewsService> { createService(get(), get()) }

            single {
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            }

            single {
                val interceptor = HttpLoggingInterceptor {
                    Log.e(OK_HTTP, it)
                }

                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }
        }
    }


}