package com.practicum.movies.di

import com.practicum.movies.domain.movies.api.MoviesInteractor
import com.practicum.movies.domain.movies.impl.MoviesInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    single<MoviesInteractor> {
        MoviesInteractorImpl(get())
    }
}