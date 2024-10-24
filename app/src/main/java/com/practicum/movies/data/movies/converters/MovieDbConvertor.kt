package com.practicum.movies.data.movies.converters

import com.practicum.movies.data.movies.db.entity.MovieEntity
import com.practicum.movies.data.movies.dto.MovieDto
import com.practicum.movies.domain.movies.models.Movie

class MovieDbConvertor {

    fun map(movie: MovieDto): MovieEntity {
        return MovieEntity(movie.id, movie.resultType, movie.image, movie.title, movie.description)
    }

    fun map(movie: MovieEntity): Movie {
        return Movie(movie.id, movie.resultType, movie.image, movie.title, movie.description, false)
    }
}