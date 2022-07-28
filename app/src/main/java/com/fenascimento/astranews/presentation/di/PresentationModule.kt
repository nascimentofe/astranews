package com.fenascimento.astranews.presentation.di

import com.fenascimento.astranews.presentation.ui.home.HomeViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Esse arquivo organiza as dependecias da camada Presentation
 */
object PresentationModule {

    fun load() {
        loadKoinModules(viewModelModule())
    }

    private fun viewModelModule() : Module {
        return module {
            factory {
                HomeViewModel(get(), get())
            }
        }
    }

}