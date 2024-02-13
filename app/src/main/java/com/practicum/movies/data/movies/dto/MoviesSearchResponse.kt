package com.practicum.movies.data.movies.dto

import com.practicum.movies.domain.movies.models.Movie

class MoviesSearchResponse(val searchType: String,
                           val expression: String,
                           val results: List<Movie>) : Response()