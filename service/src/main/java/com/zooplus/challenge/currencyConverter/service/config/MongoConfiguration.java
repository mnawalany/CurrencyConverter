package com.zooplus.challenge.currencyConverter.service.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.zooplus.challenge.currencyConverter.service.config.mongoConverters.DateToLocalDateConverter;
import com.zooplus.challenge.currencyConverter.service.config.mongoConverters.DateToLocalDateTimeConverter;
import com.zooplus.challenge.currencyConverter.service.config.mongoConverters.LocalDateTimeToDateConverter;
import com.zooplus.challenge.currencyConverter.service.config.mongoConverters.LocalDateToDateConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.LinkedList;
import java.util.List;

//@Configuration
//@Profile(SpringProfile.NOT_TEST)
@EnableMongoRepositories(basePackages = "com.zooplus.challenge.currencyConverter.service")
public class MongoConfiguration extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "CurrencyConverter";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient("localhost");
    }

    @Override
    public CustomConversions customConversions() {
        List<Converter> converters = new LinkedList<>();
        converters.add(new DateToLocalDateTimeConverter());
        converters.add(new DateToLocalDateConverter());
        converters.add(new LocalDateTimeToDateConverter());
        converters.add(new LocalDateToDateConverter());
        return new CustomConversions(converters);
    }

}
