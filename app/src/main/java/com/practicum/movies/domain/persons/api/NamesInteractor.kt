package com.practicum.movies.domain.persons.api

import com.practicum.movies.domain.persons.models.Person
import kotlinx.coroutines.flow.Flow

interface NamesInteractor {

    fun searchNames(expression: String): Flow<Pair<List<Person>?, String?>>
}