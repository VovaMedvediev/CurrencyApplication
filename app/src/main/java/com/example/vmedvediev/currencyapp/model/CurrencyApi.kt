package com.example.vmedvediev.currencyapp.model

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApi {

    @GET("currencies")
    fun getCurrencyCodes() : Observable<CurrencyCodesResponse>

    @GET("ticker/{fromCurrencyCode}-{toCurrencyCode}")
    fun getConvertedValue(@Path("fromCurrencyCode") fromCurrencyCode: String?,
                          @Path("toCurrencyCode") toCurrencyCode: String?) : Observable<ConvertedValueResponse>
}