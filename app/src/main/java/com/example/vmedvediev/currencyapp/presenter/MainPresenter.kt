package com.example.vmedvediev.currencyapp.presenter

import android.text.Editable
import android.util.Log
import com.example.vmedvediev.currencyapp.model.NetworkManager
import com.example.vmedvediev.currencyapp.model.Currency
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.coroutines.experimental.bg

class MainPresenter(private val view: View) {

    companion object {
        private const val TAG = "MainPresenter"
    }

    private var currencies: ArrayList<Currency> = ArrayList()
    private var price: Double = 0.0

    fun makeGetCurrencyCodesRequest() = launch(UI) {
        try {
            bg {
                val currencyCodeResponse = NetworkManager.initRetrofit()
                        .getCurrencyCodes().execute().body()
                currencies = currencyCodeResponse?.currencies ?: currencies
            }.await()


            view.showCurrencyCodes(currencies)
            view.hideLoading()

        } catch (e: Exception) {
            Log.e(TAG, "makeGetCurrencyCodesRequest: ${e.message}")
            view.hideLoading()
            view.showNoCurrencyCodesError()
        }
    }

    fun prepareConvertRequest(initialCurrencyCode: String?, initialValue: Editable?,
                              convertedCurrencyCode: String?) = launch(UI) {
        try {
            if (isValidInputValue(initialValue)) {
                view.showNoInputValueError()
            } else {
                view.showConvertingLoading()

                val convertedValueResponse = makeConvertRequest(initialCurrencyCode, convertedCurrencyCode).await()

                if (convertedValueResponse?.convertedValue?.price == null) {
                    view.showConvertingError(convertedValueResponse?.error)
                } else {
                    this@MainPresenter.price = convertedValueResponse.convertedValue.price
                }
                view.showConvertedValue(convertValue(initialValue, this@MainPresenter.price))
                view.hideLoading()

            }
        } catch (e: Exception) {
            Log.e(TAG, "prepareConvertRequest: ${e.message}")
            view.hideLoading()
            view.showConvertingError(e.message)
        }
    }

    private fun makeConvertRequest(initialCurrencyCode: String?, convertedCurrencyCode: String?) = bg {
            return@bg NetworkManager.initRetrofit()
                    .getConvertedValue(initialCurrencyCode, convertedCurrencyCode).execute().body()
        }

    private fun isValidInputValue(initialValue: Editable?) : Boolean =
         if (initialValue.isNullOrBlank()) {
            view.showNoInputValueError()
            true
        } else {
            false
        }

    private fun convertValue(initialValue: Editable?, price: Double): Double = initialValue.toString().toDouble() * price

    interface View {

        fun hideLoading()

        fun showNoCurrencyCodesError()

        fun showCurrencyCodes(currencies: ArrayList<Currency>)
        
        fun showConvertingLoading()

        fun showConvertedValue(convertedValue: Double)

        fun showConvertingError(error: String?)

        fun showNoInputValueError()

    }
}