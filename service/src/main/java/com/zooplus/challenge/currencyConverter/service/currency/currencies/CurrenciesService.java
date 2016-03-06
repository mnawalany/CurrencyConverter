package com.zooplus.challenge.currencyConverter.service.currency.currencies;

import com.zooplus.challenge.currencyConverter.service.currency.Currency;

import java.util.List;

public interface CurrenciesService {

    List<Currency> getSupportedCurrencies();

    Currency getCurrencyById(String name);

}
