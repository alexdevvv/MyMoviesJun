package com.example.mymovies.data.retrofit

import com.example.mymovies.data.model.ItemMovie
import com.example.mymovies.data.model.Movies
import io.reactivex.Observable
import retrofit2.http.GET

interface MoviesApi {

    @GET("en/API/Top250TVs/k_hiwjni8r")
    fun getTopMovies(): Observable<Movies>
}