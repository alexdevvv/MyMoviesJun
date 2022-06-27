package com.example.mymovies.data.model

import com.google.gson.annotations.SerializedName

data class Genres(
    @SerializedName("genre")
    val genre: String
)