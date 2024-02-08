package com.practicum.movies.util

import android.content.Context
import com.practicum.movies.data.MoviesRepositoryImpl
import com.practicum.movies.data.network.RetrofitNetworkClient
import com.practicum.movies.domain.api.MoviesInteractor
import com.practicum.movies.domain.api.MoviesRepository
import com.practicum.movies.domain.impl.MoviesInteractorImpl
import com.practicum.movies.presentation.movies.LocalStorage
import com.practicum.movies.presentation.poster.PosterPresenter
import com.practicum.movies.presentation.poster.PosterView

object Creator {
//    private fun getMoviesRepository(context: Context): MoviesRepository {
//        return MoviesRepositoryImpl(
//            RetrofitNetworkClient(context),
//            LocalStorage(context.getSharedPreferences("local_storage", Context.MODE_PRIVATE)),
//        )
//    }
//
//    fun provideMoviesInteractor(context: Context): MoviesInteractor {
//        return MoviesInteractorImpl(getMoviesRepository(context))
//    }

    fun providePosterPresenter(
        posterView: PosterView,
        imageUrl: String,
    ): PosterPresenter {
        return PosterPresenter(posterView, imageUrl)
    }
}