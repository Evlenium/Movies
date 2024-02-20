package com.practicum.movies.di

import com.practicum.movies.domain.movies.api.MoviesInteractor
import com.practicum.movies.domain.movies.impl.MoviesInteractorImpl
import com.practicum.movies.domain.persons.api.NamesInteractor
import com.practicum.movies.domain.persons.impl.NamesInteracrorImpl
import org.koin.dsl.module

val interactorModule = module {

    single<MoviesInteractor> {
        MoviesInteractorImpl(get())
    }
    single<NamesInteractor> {
        NamesInteracrorImpl(get())
    }
}