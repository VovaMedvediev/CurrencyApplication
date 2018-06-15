package com.example.vmedvediev.currencyapp.interactor

import com.example.vmedvediev.currencyapp.model.CurrencyCodesProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CurrencyInteractor(var output: InteractorOutput) : Interactor {

    override fun getCurrencyCodes() {
        val repository = CurrencyCodesProvider.provideCurrencyRepository()
        repository.loadCurrencyCodes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    output.onCurrencyCodesLoaded(result.currencies)
                }, { error ->
                    output.onCurrencyCodesNotAvailable(error.message)
                })
    }

    override fun getConvertedValue(initialCurrencyCode: String?, convertedCurrencyCode: String?, initialValue: Double) {
        val repository = CurrencyCodesProvider.provideCurrencyRepository()
        repository.loadConvertedValue(initialCurrencyCode, convertedCurrencyCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    if (result?.convertedValue?.price == null) {
                        output.onConvertedValueNotAvailable(result.error)
                    } else {
                        val price = result.convertedValue.price
                        output.onConvertedValueLoaded(convertValue(initialValue, price))
                    }

                }, { error ->
                    output.onConvertedValueNotAvailable(error.message)
                })
    }

    private fun convertValue(initialValue: Double, price: Double): Double = initialValue.toString().toDouble() * price
}