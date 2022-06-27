package com.example.mymovies.data.retrofit

import com.example.mymovies.data.model.TopFilms
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("v2.2/films/top")
    fun getTopFilms(
        @Query("page")page: Int,
        @Header("X-API-KEY") token:String
    ): Observable<TopFilms>
}