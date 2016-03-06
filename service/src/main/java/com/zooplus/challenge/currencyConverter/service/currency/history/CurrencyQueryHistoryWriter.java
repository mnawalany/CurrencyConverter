package com.zooplus.challenge.currencyConverter.service.currency.history;

import com.zooplus.challenge.currencyConverter.service.currency.CurrencyQuery;
import com.zooplus.challenge.currencyConverter.service.user.User;

public interface CurrencyQueryHistoryWriter {

    void addCurrencyQuery(User user, CurrencyQuery currencyQuery);

}
