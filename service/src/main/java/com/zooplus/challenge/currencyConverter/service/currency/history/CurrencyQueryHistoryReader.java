package com.zooplus.challenge.currencyConverter.service.currency.history;

import com.zooplus.challenge.currencyConverter.service.currency.CurrencyQuery;
import com.zooplus.challenge.currencyConverter.service.user.User;

import java.util.List;

public interface CurrencyQueryHistoryReader {

    List<CurrencyQuery> getRecentCurrencyQueries(User user);

}
