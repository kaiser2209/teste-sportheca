package com.example.testeconsumoapi.domain

import com.google.gson.annotations.SerializedName

data class Bars(
    @SerializedName("Copas_do_Mundo_Vencidas")
    val copasDoMundoVencidas: Bar,
    @SerializedName("Copas_do_Mundo_Disputadas")
    val copasDoMundoDisputadas: Bar
)
