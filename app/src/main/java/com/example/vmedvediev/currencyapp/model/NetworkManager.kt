package com.example.vmedvediev.currencyapp.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {

    private const val BASE_URL = "https://api.cryptonator.com/api/"

    fun initRetrofit() : CurrencyApi {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(CurrencyApi::class.java)
    }
}