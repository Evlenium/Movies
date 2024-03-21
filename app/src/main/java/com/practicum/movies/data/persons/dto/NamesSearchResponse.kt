package com.practicum.movies.data.persons.dto

import com.practicum.movies.data.movies.dto.Response

class NamesSearchResponse(
    val searchType: String,
    val expression: String,
    val results: List<PersonDto>,
) : Response()