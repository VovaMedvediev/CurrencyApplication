package com.example.vmedvediev.currencyapp.model

object CurrencyCodesProvider {

    @JvmStatic fun provideCurrencyRepository() : CurrencyRepository {
        return CurrencyRepository(NetworkManager.initRetrofit())
    }
}