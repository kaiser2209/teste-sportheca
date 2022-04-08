package com.example.testeconsumoapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.testeconsumoapi.data.DataApi
import com.example.testeconsumoapi.databinding.ActivityMainBinding
import com.example.testeconsumoapi.domain.Result
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var dataApi: DataApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupHttpClient()
        getApiResult()
        setupRefresh()
    }

    //Método responsável por consumir a API
    private fun setupHttpClient() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sportsmatch.com.br/teste/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        dataApi = retrofit.create(DataApi::class.java)
    }

    //Função que pega o resultado da API
    private fun getApiResult() {
        binding.srlMain.isRefreshing = true
        dataApi.players?.enqueue(object : Callback<Result?> {
            override fun onResponse(call: Call<Result?>, response: Response<Result?>) {
                if(response.isSuccessful) {
                    val result = response.body()
                    result?.let { updateUI(it) }
                } else {
                    showErrorMessage()
                }

                binding.srlMain.isRefreshing = false
            }

            override fun onFailure(call: Call<Result?>, t: Throwable) {
                showErrorMessage()
                binding.srlMain.isRefreshing = false
            }

        })
    }

    //Define o refreshListener do SwipeRefreshLayout
    private fun setupRefresh() {
        binding.srlMain.setOnRefreshListener(this::getApiResult)
    }

    //Mostra uma mensagem de erro
    private fun showErrorMessage() {
        Snackbar.make(binding.root, R.string.error_api, Snackbar.LENGTH_LONG).show()
    }

    //Função que atualiza os objetos da tela
    private fun updateUI(result: Result) {
        val player = result.objects[0].player
        binding.tvUser.text = player.name
        binding.tvCountry.text = player.country
        binding.tvPosition.text = player.pos
        binding.tvAverage.text = String.format("%.3f", player.percentual)
        binding.pbPlayed.max = player.barras.copasDoMundoDisputadas.max.toInt()
        binding.pbPlayed.progress = player.barras.copasDoMundoDisputadas.pla.toInt()
        binding.tvPlayedPos.text = String.format("%dº", player.barras.copasDoMundoDisputadas.pos)
        binding.tvPlayedValue.text = String.format("%.1f", player.barras.copasDoMundoDisputadas.pla)
        binding.pbWon.max = player.barras.copasDoMundoVencidas.max.toInt()
        binding.pbWon.progress = player.barras.copasDoMundoVencidas.pla.toInt()
        binding.tvWonPos.text = String.format("%dº", player.barras.copasDoMundoVencidas.pos)
        binding.tvWonValue.text = String.format("%.1f", player.barras.copasDoMundoVencidas.pla)
        Glide.with(this).load(player.img.replace("http:", "https:")).circleCrop().into(binding.ivImg)

    }
}