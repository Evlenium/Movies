package com.practicum.movies.domain.persons.api

import com.practicum.movies.domain.persons.models.Person

interface NamesInteractor {

    fun searchNames(expression: String, consumer: NamesConsumer)

    interface NamesConsumer {
        fun consume(foundNames: List<Person>?, errorMessage: String?)
    }
}