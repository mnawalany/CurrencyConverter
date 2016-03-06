package com.zooplus.challenge.currencyConverter.service.currency.integration;

import java.math.BigDecimal;
import java.util.Map;

public interface RemoteCurrencyService {

    Map<String, String> getCurrencies();

    BigDecimal getExchangeRate(String currency);

}
