package com.practicum.movies.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.practicum.movies.data.movies.NetworkClient
import com.practicum.movies.data.movies.network.IMDbApiService
import com.practicum.movies.data.movies.network.RetrofitNetworkClient
import com.practicum.movies.presentation.movies.LocalStorage
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val FAVORITES_KEY = "FAVORITES_KEY"

val dataModule = module {

    single<IMDbApiService> {
        Retrofit.Builder()
            .baseUrl("https://tv-api.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IMDbApiService::class.java)
    }
    factory { Gson() }

    single(named(FAVORITES_KEY)) {
        androidContext().getSharedPreferences(FAVORITES_KEY, Context.MODE_PRIVATE)
    }

    single<LocalStorage> {
        val sharedMoviesList: SharedPreferences by inject(qualifier = named(FAVORITES_KEY))
        LocalStorage(sharedMoviesList)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get(), androidContext())
    }
}