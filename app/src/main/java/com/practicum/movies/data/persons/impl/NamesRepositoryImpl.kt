package com.practicum.movies.data.persons.impl

import com.practicum.movies.data.movies.NetworkClient
import com.practicum.movies.data.persons.dto.NamesSearchRequest
import com.practicum.movies.data.persons.dto.NamesSearchResponse
import com.practicum.movies.domain.persons.api.NamesRepository
import com.practicum.movies.domain.persons.models.Person
import com.practicum.movies.util.Resource

class NamesRepositoryImpl(private val networkClient: NetworkClient) : NamesRepository {

    override fun searchNames(expression: String): Resource<List<Person>> {
        val response = networkClient.doRequest(NamesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }

            200 -> {
                with(response as NamesSearchResponse) {
                    Resource.Success(results.map {
                        Person(
                            id = it.id,
                            name = it.title,
                            description = it.description,
                            photoUrl = it.image
                        )
                    })
                }
            }

            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }
}