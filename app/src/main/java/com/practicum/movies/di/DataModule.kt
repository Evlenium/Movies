package com.practicum.movies.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.gson.Gson
import com.practicum.movies.data.movies.NetworkClient
import com.practicum.movies.data.movies.db.AppDatabase
import com.practicum.movies.data.movies.network.IMDbApiService
import com.practicum.movies.data.movies.network.RetrofitNetworkClient
import com.practicum.movies.presentation.movies.LocalStorage
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .build()
            )
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

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .build()
    }
}