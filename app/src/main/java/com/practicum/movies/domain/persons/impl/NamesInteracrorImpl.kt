package com.practicum.movies.domain.persons.impl

import com.practicum.movies.domain.persons.api.NamesInteractor
import com.practicum.movies.domain.persons.api.NamesRepository
import com.practicum.movies.domain.persons.models.Person
import com.practicum.movies.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NamesInteracrorImpl(private val repository: NamesRepository) : NamesInteractor {

    override fun searchNames(expression: String): Flow<Pair<List<Person>?, String?>> {
        return repository.searchNames(expression).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }
}