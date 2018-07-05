package com.example.vmedvediev.currencyapp

import com.example.vmedvediev.currencyapp.interactor.InteractorOutput
import com.example.vmedvediev.currencyapp.model.ConvertedValueResponse
import com.example.vmedvediev.currencyapp.model.Currency
import com.example.vmedvediev.currencyapp.model.CurrencyCodesProvider
import com.example.vmedvediev.currencyapp.model.CurrencyCodesResponse
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

class CurrencyInteractorTest {

    private val testGetCurrencyCodesObserver: TestObserver<CurrencyCodesResponse> = TestObserver()
    private val testGetConvertedValueObserver: TestObserver<ConvertedValueResponse> = TestObserver()
    private val repository = CurrencyCodesProvider.provideCurrencyRepository()

    @Test
    fun testGetCurrencyCodes() {
        repository.loadCurrencyCodes().subscribe(testGetCurrencyCodesObserver)
        testGetCurrencyCodesObserver.assertValue { result -> result.currencies.contains(Currency("007", "007", null))}
        testGetCurrencyCodesObserver.assertValue { result -> result.currencies.size > 0 }
        testGetCurrencyCodesObserver.assertNoErrors()
        testGetCurrencyCodesObserver.assertComplete()
    }

    @Test
    fun testGetConvertedValue() {
        repository.loadConvertedValue("uah", "usd").subscribe(testGetConvertedValueObserver)
        testGetConvertedValueObserver.assertValue { result -> result.error == ""}
        testGetConvertedValueObserver.assertNoErrors()
        testGetConvertedValueObserver.assertComplete()
    }

    @Test
    fun testGetConvertedValueErrorPairNotFound() {
        repository.loadConvertedValue("8bit", "acoin").subscribe(testGetConvertedValueObserver)
        testGetConvertedValueObserver.assertValue { result -> result.error == "Pair not found"}
        testGetConvertedValueObserver.assertNoErrors()
        testGetConvertedValueObserver.assertComplete()
    }

    @Test
    fun testGetConvertedValueErrorSelectDifferentCurrencies() {
        repository.loadConvertedValue("8bit", "8bit").subscribe(testGetConvertedValueObserver)
        testGetConvertedValueObserver.assertValue { result -> result.error == "Select different currencies"}
        testGetConvertedValueObserver.assertNoErrors()
        testGetConvertedValueObserver.assertComplete()
    }
}