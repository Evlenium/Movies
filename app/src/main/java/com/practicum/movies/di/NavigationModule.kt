package com.practicum.movies.di

import com.practicum.movies.core.navigation.Router
import com.practicum.movies.core.navigation.RouterImpl
import org.koin.dsl.module

val navigationModule = module {
    val router = RouterImpl()

    single<Router> { router }
    single { router.navigatorHolder }
}