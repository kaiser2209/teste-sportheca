package com.example.testeconsumoapi.data

import retrofit2.Call
import retrofit2.http.GET
import com.example.testeconsumoapi.domain.Result

interface DataApi {
    @get:GET("teste.json")
    val players: Call<Result?>?
}