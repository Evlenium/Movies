package com.practicum.movies.di

import com.practicum.movies.domain.api.MoviesInteractor
import com.practicum.movies.domain.impl.MoviesInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    single<MoviesInteractor> {
        MoviesInteractorImpl(get())
    }
}