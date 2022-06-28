package com.example.mymovies.data.retrofit

import com.example.mymovies.data.model.Film
import com.example.mymovies.data.model.TopFilms
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("v2.2/films/top")
    fun getTopFilms(
        @Query("page")page: Int,
        @Header("X-API-KEY") token:String
    ): Observable<TopFilms>


    @GET("v2.2/films/{id}")
    fun getFilmById(
        @Path("id") id: Int,
        @Header("X-API-KEY") token:String
    ): Observable<Film>
}