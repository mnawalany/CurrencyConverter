package com.zooplus.challenge.currencyConverter.service.currency.currencies.service;

import com.zooplus.challenge.currencyConverter.service.currency.Currency;
import com.zooplus.challenge.currencyConverter.service.currency.integration.RemoteCurrencyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.googlecode.catchexception.apis.BDDCatchException.caughtException;
import static com.googlecode.catchexception.apis.BDDCatchException.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class RemoteCurrenciesServiceTest {

    @Mock
    private RemoteCurrencyService remoteCurrencyService;

    @InjectMocks
    private RemoteCurrenciesService remoteCurrenciesService;

    @Test
    public void shouldReturnListOfCurrenciesSortedById() {
        // Given
        Map<String, String> currenciesMap = new HashMap<>();
        currenciesMap.put("USD", "dollar");
        currenciesMap.put("GBP", "pound");
        currenciesMap.put("PLN", "zloty");

        given(remoteCurrencyService.getCurrencies()).willReturn(currenciesMap);

        // When
        List<Currency> currencies = remoteCurrenciesService.getSupportedCurrencies();

        // Then
        assertThat(currencies.get(0).getId()).isEqualTo("GBP");
        assertThat(currencies.get(0).getName()).isEqualTo("pound");
        assertThat(currencies.get(1).getId()).isEqualTo("PLN");
        assertThat(currencies.get(1).getName()).isEqualTo("zloty");
        assertThat(currencies.get(2).getId()).isEqualTo("USD");
        assertThat(currencies.get(2).getName()).isEqualTo("dollar");
    }

    @Test
    public void shouldReturnCurrencyById() {
        // Given
        Map<String, String> currenciesMap = new HashMap<>();
        currenciesMap.put("USD", "dollar");
        currenciesMap.put("GBP", "pound");
        currenciesMap.put("PLN", "zloty");

        given(remoteCurrencyService.getCurrencies()).willReturn(currenciesMap);

        // When
        Currency currency = remoteCurrenciesService.getCurrencyById("GBP");

        // Then
        assertThat(currency.getId()).isEqualTo("GBP");
        assertThat(currency.getName()).isEqualTo("pound");
    }

    @Test
    public void shouldThrowExceptionWhenCurrencyIsNotSupported() {
        // Given
        Map<String, String> currenciesMap = new HashMap<>();
        currenciesMap.put("USD", "dollar");
        currenciesMap.put("GBP", "pound");
        currenciesMap.put("PLN", "zloty");

        given(remoteCurrencyService.getCurrencies()).willReturn(currenciesMap);

        // When
        when(remoteCurrenciesService).getCurrencyById("EUR");

        // Then
        then(caughtException()).isInstanceOf(IllegalStateException.class)
                .hasMessage("Currency EUR not found");
    }

}