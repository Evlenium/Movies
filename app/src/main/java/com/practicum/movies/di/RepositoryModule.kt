package com.practicum.movies.di

import com.practicum.movies.data.movies.MoviesRepositoryImpl
import com.practicum.movies.domain.movies.api.MoviesRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<MoviesRepository> {
        MoviesRepositoryImpl(get(), get())
    }
}