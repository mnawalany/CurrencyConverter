package com.zooplus.challenge.currencyConverter.service.currency.integration.dummy;

import com.zooplus.challenge.currencyConverter.service.SpringProfile;
import com.zooplus.challenge.currencyConverter.service.currency.integration.RemoteCurrencyService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Profile(SpringProfile.TEST)
class DummyRemoteCurrencyService implements RemoteCurrencyService {

    @Override
    public Map<String, String> getCurrencies() {
        return new HashMap<>();
    }

    @Override
    public BigDecimal getExchangeRate(String currency) {
        return new BigDecimal(0);
    }
}
