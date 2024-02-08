package com.practicum.movies

import android.app.Application
import com.practicum.movies.di.dataModule
import com.practicum.movies.di.interactorModule
import com.practicum.movies.di.repositoryModule
import com.practicum.movies.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MoviesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MoviesApplication)
            modules(dataModule, repositoryModule, interactorModule, viewModelModule)
        }
    }
}