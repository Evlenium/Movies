package com.practicum.movies.di

import com.practicum.movies.data.cast.converters.MovieCastConverter
import com.practicum.movies.data.movies.MoviesRepositoryImpl
import com.practicum.movies.data.persons.impl.NamesRepositoryImpl
import com.practicum.movies.domain.movies.api.MoviesRepository
import com.practicum.movies.domain.persons.api.NamesRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { MovieCastConverter() }
    single<MoviesRepository> {
        MoviesRepositoryImpl(get(), get(), get())
    }
    single<NamesRepository> {
        NamesRepositoryImpl(get())
    }
}