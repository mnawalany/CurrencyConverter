package com.zooplus.challenge.currencyConverter.service.config;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import org.springframework.context.annotation.Configuration;

@Configuration
//@Profile(SpringProfile.TEST)
public class MongoTestConfiguration extends MongoConfiguration {

    @Override
    public Mongo mongo() throws Exception {
        Fongo fongo = new Fongo("localhost");
        return fongo.getMongo();
    }

}
