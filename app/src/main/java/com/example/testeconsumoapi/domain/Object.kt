package com.example.testeconsumoapi.domain

import com.google.gson.annotations.SerializedName

data class Object(
    @SerializedName("Player")
    val player: Player
)
