package com.practicum.movies.di

import com.practicum.movies.presentation.movies.MoviesSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MoviesSearchViewModel(get())
    }
}