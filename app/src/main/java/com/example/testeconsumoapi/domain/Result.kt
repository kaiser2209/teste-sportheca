package com.example.testeconsumoapi.domain

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("Status")
    val status: Int,
    @SerializedName("Object")
    val objects: List<Object>
)
