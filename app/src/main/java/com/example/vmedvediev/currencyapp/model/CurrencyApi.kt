package com.example.vmedvediev.currencyapp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApi {

    @GET("currencies")
    fun getCurrencyCodes() : Call<CurrencyCodesResponse>

    @GET("ticker/{fromCurrency}-{toCurrency}")
    fun getConvertedValue(@Path("fromCurrency") fromCurrency: String?,
                          @Path("toCurrency") toCurrency: String?) : Call<ConvertedValueResponse>
}