package com.example.vmedvediev.currencyapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrencyCodesResponse(@SerializedName("rows") val currencies: ArrayList<Currency>)

data class Currency(@SerializedName("code") val code: String, @SerializedName("name") val name: String,
                    var isInitialCurrency: Boolean)

data class ConvertedValueResponse(@SerializedName("ticker") val convertedValue: ConvertedValue, @Expose val error: String)

data class ConvertedValue(@Expose val price: Double)