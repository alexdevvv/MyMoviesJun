package com.example.mymovies.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mymovies.data.model.Film
import com.example.mymovies.data.model.TopFilms

@Dao
interface FilmsDao {

    @Query("SELECT * FROM film")
    fun getFilms(): LiveData<TopFilms>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(film: Film)

    @Query("DELETE FROM film")
    fun deleteAll()
}