package com.example.vmedvediev.currencyapp;

import android.test.suitebuilder.annotation.LargeTest;

import com.example.vmedvediev.currencyapp.model.ConvertedValueResponse;
import com.example.vmedvediev.currencyapp.model.Currency;
import com.example.vmedvediev.currencyapp.model.CurrencyCodesProvider;
import com.example.vmedvediev.currencyapp.model.CurrencyCodesResponse;
import com.example.vmedvediev.currencyapp.model.CurrencyRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import io.reactivex.observers.TestObserver;

@LargeTest
public class NewCurrencyInteractorTest {

    private CurrencyRepository repository;

    @Rule
    public TrampolineSchedulerRule trampolineSchedulerRule = new TrampolineSchedulerRule();

    @Before
    public void setUp() {
        repository = CurrencyCodesProvider.provideCurrencyRepository();
    }

    @Test
    public void testGetCurrencyCodes() {
        TestObserver<CurrencyCodesResponse> observer = repository.loadCurrencyCodes().test();
        observer.awaitTerminalEvent();
        observer.assertSubscribed();
        observer.assertComplete();
        observer.assertNoErrors();

        ArrayList<Currency> currencies = observer.values().get(0).getCurrencies();
        Assert.assertNotNull(currencies);
    }

    @Test
    public void testGetConvertedValue() {
        TestObserver<ConvertedValueResponse> observer = repository.loadConvertedValue("uah", "usd").test();
        observer.awaitTerminalEvent();
        observer.assertSubscribed();
        observer.assertComplete();
        observer.assertNoErrors();

        String result = observer.values().get(0).getError();
        Assert.assertEquals("", result);
    }

    @Test
    public void testGetConvertedValueErrorPairNotFound() {
        TestObserver<ConvertedValueResponse> observer = repository.loadConvertedValue("8bit", "acoin").test();
        observer.awaitTerminalEvent();
        observer.assertSubscribed();
        observer.assertComplete();
        observer.assertNoErrors();

        String result = observer.values().get(0).getError();
        Assert.assertEquals("Pair not found", result);
    }

    @Test
    public void testGetConvertedValueErrorSelectDifferentCurrencies() {
        TestObserver<ConvertedValueResponse> observer = repository.loadConvertedValue("uah", "uah").test();
        observer.awaitTerminalEvent();
        observer.assertSubscribed();
        observer.assertComplete();
        observer.assertNoErrors();

        String result = observer.values().get(0).getError();
        Assert.assertEquals("Select different currencies", result);
    }
}
