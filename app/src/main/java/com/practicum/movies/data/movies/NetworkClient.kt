package com.practicum.movies.data.movies

import com.practicum.movies.data.movies.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}