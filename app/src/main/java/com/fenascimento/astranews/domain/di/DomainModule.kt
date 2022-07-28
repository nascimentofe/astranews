package com.fenascimento.astranews.domain.di

import com.fenascimento.astranews.domain.GetLatestPostsTitleContainsUseCase
import com.fenascimento.astranews.domain.GetLatestPostsUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {

    fun load() {
        loadKoinModules(useCaseModule())
    }

    private fun useCaseModule(): Module {
        return module {
            factory { GetLatestPostsUseCase(get()) }
            factory { GetLatestPostsTitleContainsUseCase(get()) }
        }
    }

}