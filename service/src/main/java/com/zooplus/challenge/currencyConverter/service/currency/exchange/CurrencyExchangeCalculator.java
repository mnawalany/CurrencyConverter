package com.zooplus.challenge.currencyConverter.service.currency.exchange;

import com.zooplus.challenge.currencyConverter.service.currency.CurrencyQuery;
import com.zooplus.challenge.currencyConverter.service.user.User;

import java.math.BigDecimal;

public interface CurrencyExchangeCalculator {

    CurrencyQuery calculateExchange(User user, String source, String target, BigDecimal value);

}
