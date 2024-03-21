package com.practicum.movies.data.movies.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.movies.data.movies.db.dao.MovieDao
import com.practicum.movies.data.movies.db.entity.MovieEntity

@Database(version = 1, entities = [MovieEntity::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}
