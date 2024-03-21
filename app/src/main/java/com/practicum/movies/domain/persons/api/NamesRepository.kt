package com.practicum.movies.domain.persons.api

import com.practicum.movies.domain.persons.models.Person
import com.practicum.movies.util.Resource
import kotlinx.coroutines.flow.Flow


interface NamesRepository {
    fun searchNames(expression: String): Flow<Resource<List<Person>>>
}