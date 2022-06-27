package com.example.mymovies.data.model

import com.google.gson.annotations.SerializedName

data class Countries(
    @SerializedName("country")
    val country: String
)