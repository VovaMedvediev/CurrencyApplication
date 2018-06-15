package com.example.vmedvediev.currencyapp.interactor

import android.text.Editable

interface Interactor {

    fun getCurrencyCodes()

    fun getConvertedValue(initialCurrencyCode: String?, convertedCurrencyCode: String?, initialValue: Double)
}