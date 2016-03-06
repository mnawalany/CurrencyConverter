package com.zooplus.challenge.currencyConverter.service.currency.currencies.service;

import com.zooplus.challenge.currencyConverter.service.currency.Currency;
import com.zooplus.challenge.currencyConverter.service.currency.currencies.CurrenciesService;
import com.zooplus.challenge.currencyConverter.service.currency.integration.RemoteCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
class RemoteCurrenciesService implements CurrenciesService {

    @Autowired
    private RemoteCurrencyService remoteCurrencyService;

    @Override
    public List<Currency> getSupportedCurrencies() {
        Map<String, String> currenciesMap = remoteCurrencyService.getCurrencies();
        List<Currency> currencies = new LinkedList<>();
        for (Map.Entry<String, String> currencyEntry : currenciesMap.entrySet()) {
            currencies.add(new Currency(currencyEntry.getKey(), currencyEntry.getValue()));
        }
        return currencies.stream()
                .sorted((c1, c2) -> c1.getId().compareTo(c2.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Currency getCurrencyById(String name) {
        Map<String, String> currenciesMap = remoteCurrencyService.getCurrencies();
        if (!currenciesMap.containsKey(name)) {
            throw new IllegalStateException("Currency "+name+" not found");
        }
        return new Currency(name, currenciesMap.get(name));
    }
}
