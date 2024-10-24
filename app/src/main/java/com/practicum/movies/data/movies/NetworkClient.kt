package com.practicum.movies.data.movies

import com.practicum.movies.data.movies.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}