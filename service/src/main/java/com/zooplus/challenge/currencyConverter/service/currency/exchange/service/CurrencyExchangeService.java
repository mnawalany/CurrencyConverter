package com.zooplus.challenge.currencyConverter.service.currency.exchange.service;

import com.zooplus.challenge.currencyConverter.service.currency.Currency;
import com.zooplus.challenge.currencyConverter.service.currency.CurrencyQuery;
import com.zooplus.challenge.currencyConverter.service.currency.currencies.CurrenciesService;
import com.zooplus.challenge.currencyConverter.service.currency.exchange.CurrencyExchangeCalculator;
import com.zooplus.challenge.currencyConverter.service.currency.history.CurrencyQueryHistoryWriter;
import com.zooplus.challenge.currencyConverter.service.currency.integration.RemoteCurrencyService;
import com.zooplus.challenge.currencyConverter.service.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static java.math.BigDecimal.ONE;

@Service
class CurrencyExchangeService implements CurrencyExchangeCalculator {

    public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    public static final int SCALE = 2;
    public static final int BIGGER_SCALE = 4;

    @Autowired
    private CurrenciesService currenciesService;

    @Autowired
    private RemoteCurrencyService remoteCurrencyService;

    @Autowired
    private CurrencyQueryHistoryWriter currencyQueryHistoryWriter;

    @Override
    public CurrencyQuery calculateExchange(User user, String source, String target, BigDecimal value) {
        Currency sourceCurrency = currenciesService.getCurrencyById(source);
        Currency targetCurrency = currenciesService.getCurrencyById(target);
        BigDecimal sourceExchangeRate = remoteCurrencyService.getExchangeRate(source).setScale(BIGGER_SCALE, ROUNDING_MODE);
        BigDecimal targetExchangeRate = remoteCurrencyService.getExchangeRate(target).setScale(BIGGER_SCALE, ROUNDING_MODE);;

        BigDecimal roundedValue = value.setScale(SCALE, ROUNDING_MODE);

        BigDecimal exchangeRate = ONE.divide(sourceExchangeRate, BIGGER_SCALE, ROUNDING_MODE).multiply(targetExchangeRate);
        BigDecimal result = roundedValue.multiply(exchangeRate).setScale(SCALE, ROUNDING_MODE);

        CurrencyQuery currencyQuery = new CurrencyQuery(LocalDateTime.now(), sourceCurrency, roundedValue, targetCurrency, result);

        currencyQueryHistoryWriter.addCurrencyQuery(user, currencyQuery);

        return currencyQuery;
    }
}
