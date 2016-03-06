package com.zooplus.challenge.currencyConverter.client.currency;

import com.zooplus.challenge.currencyConverter.service.currency.Currency;
import com.zooplus.challenge.currencyConverter.service.currency.CurrencyQuery;
import com.zooplus.challenge.currencyConverter.service.currency.currencies.CurrenciesService;
import com.zooplus.challenge.currencyConverter.service.currency.exchange.CurrencyExchangeCalculator;
import com.zooplus.challenge.currencyConverter.service.currency.history.CurrencyQueryHistoryReader;
import com.zooplus.challenge.currencyConverter.service.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static io.codearte.catchexception.shade.mockito.Mockito.mock;
import static io.codearte.catchexception.shade.mockito.Mockito.when;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyControllerTest {

    @Mock
    private CurrencyExchangeCalculator currencyExchangeCalculator;

    @Mock
    private CurrenciesService currenciesService;

    @Mock
    private CurrencyQueryHistoryReader currencyQueryHistoryReader;

    @InjectMocks
    private CurrencyController currencyController;

    @Test
    public void shouldReturnMainPageWhenNoRecentQueries() {
        // Given
        User user = new User("id", "some username");

        List<Currency> currencyList = new LinkedList<>();
        currencyList.add(new Currency("USD", "dollar"));
        given(currenciesService.getSupportedCurrencies()).willReturn(currencyList);

        List<CurrencyQuery> recentQueries = new LinkedList<>();
        given(currencyQueryHistoryReader.getRecentCurrencyQueries(user)).willReturn(recentQueries);

        // When
        ModelAndView mav = currencyController.mainPage(user);

        // Then
        assertThat(mav.getViewName()).isEqualTo("currency/main");
        assertThat(mav.getModel().get("username")).isEqualTo("some username");
        assertThat(mav.getModel().get("currencies")).isEqualTo(currencyList);
        assertThat(mav.getModel().get("recentCurrencyQueries")).isEqualTo(recentQueries);
        CurrencyExchangeForm newExchangeForm = (CurrencyExchangeForm) mav.getModel().get("currencyExchangeForm");
        assertThat(newExchangeForm.getSourceCurrency()).isNull();
        assertThat(newExchangeForm.getTargetCurrency()).isNull();
        assertThat(newExchangeForm.getValue()).isNull();
    }

    @Test
    public void shouldReturnMainPageWhenWithRecentQueries() {
        // Given
        User user = new User("id", "some username");

        List<Currency> currencyList = new LinkedList<>();
        currencyList.add(new Currency("USD", "dollar"));
        given(currenciesService.getSupportedCurrencies()).willReturn(currencyList);

        List<CurrencyQuery> recentQueries = new LinkedList<>();
        recentQueries.add(new CurrencyQuery(now(), new Currency("source", null), null, new Currency("target", null), null));
        recentQueries.add(new CurrencyQuery(now(), null, null, null, null));
        recentQueries.add(new CurrencyQuery(now(), null, null, null, null));
        given(currencyQueryHistoryReader.getRecentCurrencyQueries(user)).willReturn(recentQueries);

        // When
        ModelAndView mav = currencyController.mainPage(user);

        // Then
        assertThat(mav.getViewName()).isEqualTo("currency/main");
        assertThat(mav.getModel().get("username")).isEqualTo("some username");
        assertThat(mav.getModel().get("currencies")).isEqualTo(currencyList);
        assertThat(mav.getModel().get("recentCurrencyQueries")).isEqualTo(recentQueries);
        CurrencyExchangeForm newExchangeForm = (CurrencyExchangeForm) mav.getModel().get("currencyExchangeForm");
        assertThat(newExchangeForm.getSourceCurrency()).isEqualTo("source");
        assertThat(newExchangeForm.getTargetCurrency()).isEqualTo("target");
        assertThat(newExchangeForm.getValue()).isNull();
    }

    @Test
    public void shouldReturnMainPageWhenSubmittingInvalidForm() {
        // Given
        User user = new User("id", "some username");

        List<Currency> currencyList = new LinkedList<>();
        currencyList.add(new Currency("USD", "dollar"));
        given(currenciesService.getSupportedCurrencies()).willReturn(currencyList);

        List<CurrencyQuery> recentQueries = new LinkedList<>();
        recentQueries.add(new CurrencyQuery(now(), new Currency("source", null), null, new Currency("target", null), null));
        recentQueries.add(new CurrencyQuery(now(), null, null, null, null));
        recentQueries.add(new CurrencyQuery(now(), null, null, null, null));
        given(currencyQueryHistoryReader.getRecentCurrencyQueries(user)).willReturn(recentQueries);

        CurrencyExchangeForm currencyExchangeForm = new CurrencyExchangeForm();

        BindingResult errors = mock(BindingResult.class);
        when(errors.hasErrors()).thenReturn(true);

        // When
        ModelAndView mav = currencyController.calculateExchange(user, currencyExchangeForm, errors);

        // Then
        assertThat(mav.getViewName()).isEqualTo("currency/main");
        assertThat(mav.getModel().get("username")).isEqualTo("some username");
        assertThat(mav.getModel().get("currencies")).isEqualTo(currencyList);
        assertThat(mav.getModel().get("recentCurrencyQueries")).isEqualTo(recentQueries);
        assertThat(mav.getModel().get("currencyExchangeForm")).isEqualTo(currencyExchangeForm);
    }

    @Test
    public void shouldSubmitValidForm() {
        // Given
        User user = new User("id", "some username");

        List<Currency> currencyList = new LinkedList<>();
        currencyList.add(new Currency("USD", "dollar"));
        given(currenciesService.getSupportedCurrencies()).willReturn(currencyList);

        List<CurrencyQuery> recentQueries = new LinkedList<>();
        recentQueries.add(new CurrencyQuery(now(), new Currency("source", null), null, new Currency("target", null), null));
        recentQueries.add(new CurrencyQuery(now(), null, null, null, null));
        recentQueries.add(new CurrencyQuery(now(), null, null, null, null));
        given(currencyQueryHistoryReader.getRecentCurrencyQueries(user)).willReturn(recentQueries);

        CurrencyExchangeForm currencyExchangeForm = new CurrencyExchangeForm();
        currencyExchangeForm.setSourceCurrency("source");
        currencyExchangeForm.setTargetCurrency("target");
        currencyExchangeForm.setValue("123");

        BindingResult errors = mock(BindingResult.class);
        when(errors.hasErrors()).thenReturn(false);

        CurrencyQuery result = new CurrencyQuery(now(), null, null, null, null);
        given(currencyExchangeCalculator.calculateExchange(user, "source", "target", new BigDecimal(123))).willReturn(result);

        // When
        ModelAndView mav = currencyController.calculateExchange(user, currencyExchangeForm, errors);

        // Then
        assertThat(mav.getViewName()).isEqualTo("currency/main");
        assertThat(mav.getModel().get("username")).isEqualTo("some username");
        assertThat(mav.getModel().get("currencies")).isEqualTo(currencyList);
        assertThat(mav.getModel().get("recentCurrencyQueries")).isEqualTo(recentQueries);
        assertThat(mav.getModel().get("result")).isEqualTo(result);

        CurrencyExchangeForm newExchangeForm = (CurrencyExchangeForm) mav.getModel().get("currencyExchangeForm");
        assertThat(newExchangeForm.getSourceCurrency()).isEqualTo("source");
        assertThat(newExchangeForm.getTargetCurrency()).isEqualTo("target");
        assertThat(newExchangeForm.getValue()).isNull();
    }

}