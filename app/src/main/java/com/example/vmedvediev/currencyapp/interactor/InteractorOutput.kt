package com.example.vmedvediev.currencyapp.interactor

import com.example.vmedvediev.currencyapp.model.Currency

interface InteractorOutput {

    fun onCurrencyCodesLoaded(currencies: ArrayList<Currency>)

    fun onCurrencyCodesNotAvailable(message: String?)

    fun onConvertedValueLoaded(convertedValue: Double)

    fun onConvertedValueNotAvailable(message: String?)
}