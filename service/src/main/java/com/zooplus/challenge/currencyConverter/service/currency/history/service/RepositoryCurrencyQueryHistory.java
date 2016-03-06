package com.zooplus.challenge.currencyConverter.service.currency.history.service;

import com.zooplus.challenge.currencyConverter.service.currency.CurrencyQuery;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "currencyQueryHistory")
public class RepositoryCurrencyQueryHistory {

    @Id
    private final String userId;

    private final List<CurrencyQuery> history;

    public RepositoryCurrencyQueryHistory(String userId, List<CurrencyQuery> history) {
        this.userId = userId;
        this.history = history;
    }

    public String getUserId() {
        return userId;
    }

    public List<CurrencyQuery> getHistory() {
        return history;
    }
}
