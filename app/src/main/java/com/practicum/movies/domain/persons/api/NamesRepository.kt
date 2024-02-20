package com.practicum.movies.domain.persons.api

import com.practicum.movies.domain.persons.models.Person
import com.practicum.movies.util.Resource


interface NamesRepository {
    fun searchNames(expression: String): Resource<List<Person>>
}