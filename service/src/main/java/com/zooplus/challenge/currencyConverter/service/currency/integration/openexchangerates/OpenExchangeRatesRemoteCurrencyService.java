package com.zooplus.challenge.currencyConverter.service.currency.integration.openexchangerates;

import com.zooplus.challenge.currencyConverter.service.SpringProfile;
import com.zooplus.challenge.currencyConverter.service.currency.integration.RemoteCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
@Profile(SpringProfile.NOT_TEST)
class OpenExchangeRatesRemoteCurrencyService implements RemoteCurrencyService {

    @Autowired
    private OpenExchangeRatesConnector openExchangeRatesConnector;

    @Override
    public Map<String, String> getCurrencies() {
        return openExchangeRatesConnector.getCurrencies();
    }

    @Override
    public BigDecimal getExchangeRate(String currency) {
        OpenExchangeRatesResponse responseObject = openExchangeRatesConnector.getExchangeRates();
        return responseObject.getRates().get(currency);
    }
}
