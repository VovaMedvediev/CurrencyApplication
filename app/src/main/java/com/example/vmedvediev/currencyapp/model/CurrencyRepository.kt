package com.example.vmedvediev.currencyapp.model

import io.reactivex.Observable

class CurrencyRepository(private val currencyApi: CurrencyApi) {

    fun loadCurrencyCodes() : Observable<CurrencyCodesResponse> {
        return currencyApi.getCurrencyCodes()
    }

    fun loadConvertedValue(fromCurrencyCode: String?, toCurrencyCode: String?) : Observable<ConvertedValueResponse> {
        return currencyApi.getConvertedValue(fromCurrencyCode, toCurrencyCode)
    }
}