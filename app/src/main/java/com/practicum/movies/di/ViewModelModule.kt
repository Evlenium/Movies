package com.practicum.movies.di

import com.practicum.movies.presentation.cast.MoviesCastViewModel
import com.practicum.movies.presentation.details.AboutViewModel
import com.practicum.movies.presentation.details.PosterViewModel
import com.practicum.movies.presentation.history.HistoryViewModel
import com.practicum.movies.presentation.movies.MoviesSearchViewModel
import com.practicum.movies.presentation.persons.NamesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MoviesSearchViewModel(get())
    }
    viewModel { (movieId: String) ->
        AboutViewModel(movieId, get())
    }
    viewModel { (posterUrl: String) ->
        PosterViewModel(posterUrl)
    }
    viewModel { (movieId: String) ->
        MoviesCastViewModel(movieId, get())
    }
    viewModel {
        NamesViewModel(androidContext(), get())
    }
    viewModel {
        HistoryViewModel(androidContext(), get())
    }
}