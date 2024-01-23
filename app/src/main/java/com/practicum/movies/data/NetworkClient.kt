package com.practicum.movies.data

import com.practicum.movies.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}