package com.fenascimento.astranews

import android.app.Application
import com.fenascimento.astranews.data.di.DataModule
import com.fenascimento.astranews.domain.di.DomainModule
import com.fenascimento.astranews.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Essa classe representa o ponto de entrada no aplicativo
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }

        /**
         * Carregar os módulos
         */
        PresentationModule.load()
        DataModule.load()
        DomainModule.load()
    }

}