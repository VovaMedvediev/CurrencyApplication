package com.example.vmedvediev.currencyapp.presenter

import android.text.Editable
import android.util.Log
import com.example.vmedvediev.currencyapp.interactor.CurrencyInteractor
import com.example.vmedvediev.currencyapp.interactor.InteractorOutput
import com.example.vmedvediev.currencyapp.model.NetworkManager
import com.example.vmedvediev.currencyapp.model.Currency
import org.jetbrains.anko.coroutines.experimental.bg
import timber.log.Timber

class MainPresenter(private val view: View) : InteractorOutput {

    private var interactor = CurrencyInteractor(this)

    fun getCurrencyCodes() {
        interactor.getCurrencyCodes()
    }

    fun prepareConvertRequest(initialCurrencyCode: String?, convertedCurrencyCode: String?, initialValue: Editable?) {
        if (isInputValueValid(initialValue)) {
            view.showNoInputValueError()
        } else {
            view.showConvertingLoading()
            interactor.getConvertedValue(initialCurrencyCode, convertedCurrencyCode, initialValue.toString().toDouble())
        }
    }

    override fun onCurrencyCodesLoaded(currencies: ArrayList<Currency>) {
        view.showCurrencyCodes(currencies)
        view.hideLoading()
    }

    override fun onCurrencyCodesNotAvailable() {
        view.hideLoading()
        view.showNoCurrencyCodesError()
    }

    override fun onConvertedValueLoaded(convertedValue: Double) {
        view.hideLoading()
        view.showConvertedValue(convertedValue)
    }

    override fun onConvertedValueNotAvailable(message: String?) {
        view.hideLoading()
        view.showConvertingError(message)
    }

    private fun isInputValueValid(initialValue: Editable?) : Boolean =
            initialValue.isNullOrBlank()

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