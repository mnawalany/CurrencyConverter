package com.zooplus.challenge.currencyConverter.service.currency.integration.openexchangerates;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zooplus.challenge.currencyConverter.service.SpringProfile;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@Profile(SpringProfile.NOT_TEST)
class OpenExchangeRatesConnector {

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Value("${openExchangeRate.url}")
    private String openExchangeRateUrl;

    @Value("${openExchangeRate.uri.currencies}")
    private String currenciesUri;

    @Value("${openExchangeRate.uri.rates}")
    private String ratesUri;

    @Value("${openExchangeRate.app.id}")
    private String appId;

    private HttpClient client = HttpClientBuilder.create().build();

    @Cacheable("OpenExchangeRates-currencies")
    public Map<String, String> getCurrencies() {
        return sendGet(currenciesUri, Map.class);
    }

    @Cacheable("OpenExchangeRates-exchangeRates")
    public OpenExchangeRatesResponse getExchangeRates() {
        return sendGet(ratesUri, OpenExchangeRatesResponse.class);
    }

    private <T> T sendGet(String uri, Class<T> clazz) {
        HttpGet request = new HttpGet(openExchangeRateUrl+uri+"?app_id="+appId);
        try {
            HttpResponse response = client.execute(request);
            return jacksonObjectMapper.readValue(response.getEntity().getContent(), clazz);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
