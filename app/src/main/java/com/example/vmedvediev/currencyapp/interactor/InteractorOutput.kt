package com.example.vmedvediev.currencyapp.interactor

import com.example.vmedvediev.currencyapp.model.Currency

interface InteractorOutput {

    fun onCurrencyCodesLoaded(currencies: ArrayList<Currency>)

    fun onCurrencyCodesNotAvailable()

    fun onConvertedValueLoaded(convertedValue: Double)

    fun onConvertedValueNotAvailable(message: String?)
}