package com.example.vmedvediev.currencyapp.model

object CurrencyCodesProvider {

    fun provideCurrencyRepository() : CurrencyRepository {
        return CurrencyRepository(NetworkManager.initRetrofit())
    }
}