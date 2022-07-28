package com.fenascimento.astranews

import com.fenascimento.astranews.data.repository.PostRepository
import com.fenascimento.astranews.data.repository.PostRepositoryImpl
import com.fenascimento.astranews.data.services.SpaceFlightNewsService
import com.fenascimento.astranews.domain.GetLatestPostsTitleContainsUseCase
import com.fenascimento.astranews.domain.GetLatestPostsUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun configureDomainModuleForTest() = module {
    factory { GetLatestPostsUseCase(get()) }
    factory { GetLatestPostsTitleContainsUseCase(get()) }
}

fun configureDataModuleForTest(baseURL: String) =  module {

    // instanciar SpaceFlightNewsService
    single {
        val factory =  Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .build()
            .create(SpaceFlightNewsService::class.java)
    }


    // instaciar o PostRepository
    single<PostRepository> {
        PostRepositoryImpl(get())
    }
}