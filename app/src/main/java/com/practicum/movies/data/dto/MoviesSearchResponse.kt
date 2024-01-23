package com.practicum.movies.data.dto

import com.practicum.movies.domain.models.Movie

class MoviesSearchResponse(val searchType: String,
                           val expression: String,
                           val results: List<Movie>) : Response()