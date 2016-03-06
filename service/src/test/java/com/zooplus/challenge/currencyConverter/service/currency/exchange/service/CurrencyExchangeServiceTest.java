package com.zooplus.challenge.currencyConverter.service.currency.exchange.service;


import com.zooplus.challenge.currencyConverter.service.currency.Currency;
import com.zooplus.challenge.currencyConverter.service.currency.CurrencyQuery;
import com.zooplus.challenge.currencyConverter.service.currency.currencies.CurrenciesService;
import com.zooplus.challenge.currencyConverter.service.currency.history.CurrencyQueryHistoryWriter;
import com.zooplus.challenge.currencyConverter.service.currency.integration.RemoteCurrencyService;
import com.zooplus.challenge.currencyConverter.service.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyExchangeServiceTest {

    @Mock
    private CurrenciesService currenciesService;

    @Mock
    private RemoteCurrencyService remoteCurrencyService;

    @Mock
    private CurrencyQueryHistoryWriter currencyQueryHistoryWriter;

    @InjectMocks
    private CurrencyExchangeService currencyExchangeService;

    @Test
    public void shouldCalculateExchange() {
        // Given
        User user = new User("id", "username");
        String source = "EUR";
        String target = "USD";
        BigDecimal value = new BigDecimal(100);

        Currency sourceCurrency = new Currency("EUR", "euro");
        Currency targetCurrency = new Currency("USD", "dollar");
        given(currenciesService.getCurrencyById(source)).willReturn(sourceCurrency);
        given(currenciesService.getCurrencyById(target)).willReturn(targetCurrency);

        BigDecimal sourceExchangeRate = new BigDecimal(2);
        BigDecimal targetExchangeRate = new BigDecimal(3);
        given(remoteCurrencyService.getExchangeRate(source)).willReturn(sourceExchangeRate);
        given(remoteCurrencyService.getExchangeRate(target)).willReturn(targetExchangeRate);

        // When
        CurrencyQuery currencyQuery = currencyExchangeService.calculateExchange(user, source, target, value);

        // Then
        assertThat(currencyQuery.getDate()).isEqualToIgnoringSeconds(LocalDateTime.now());
        assertThat(currencyQuery.getSourceCurrency()).isEqualTo(sourceCurrency);
        assertThat(currencyQuery.getSourceValue()).isEqualByComparingTo(value);
        assertThat(currencyQuery.getTargetCurrency()).isEqualTo(targetCurrency);
        assertThat(currencyQuery.getTargetValue()).isEqualByComparingTo(new BigDecimal(150));

        verify(currencyQueryHistoryWriter).addCurrencyQuery(user, currencyQuery);
    }

}