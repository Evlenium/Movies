package com.practicum.movies.data.movies.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class MovieEntity(
    @PrimaryKey
    val id: String,
    val resultType: String,
    val image: String,
    val title: String,
    val description: String,
)
