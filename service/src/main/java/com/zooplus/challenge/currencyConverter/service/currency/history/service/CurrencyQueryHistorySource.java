package com.zooplus.challenge.currencyConverter.service.currency.history.service;

import com.zooplus.challenge.currencyConverter.service.currency.CurrencyQuery;
import com.zooplus.challenge.currencyConverter.service.currency.history.CurrencyQueryHistoryReader;
import com.zooplus.challenge.currencyConverter.service.currency.history.CurrencyQueryHistoryWriter;
import com.zooplus.challenge.currencyConverter.service.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Repository
public class CurrencyQueryHistorySource implements CurrencyQueryHistoryReader, CurrencyQueryHistoryWriter {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<CurrencyQuery> getRecentCurrencyQueries(User user) {
        Query query = new Query(Criteria.where("_id").is(user.getId()));
        query.fields().slice("history", -10);
        RepositoryCurrencyQueryHistory queryHistory = mongoTemplate.findOne(query, RepositoryCurrencyQueryHistory.class);
        if (queryHistory == null) {
            return new LinkedList<>();
        } else {
            List<CurrencyQuery> history = queryHistory.getHistory();
            Collections.reverse(history);
            return history;
        }
    }

    @Override
    public void addCurrencyQuery(User user, CurrencyQuery currencyQuery) {
        mongoTemplate.findAndModify(
                new Query(Criteria.where("_id").is(user.getId())),
                new Update().set("_id", user.getId()).push("history", currencyQuery),
                FindAndModifyOptions.options().upsert(true),
                RepositoryCurrencyQueryHistory.class
        );
    }
}
