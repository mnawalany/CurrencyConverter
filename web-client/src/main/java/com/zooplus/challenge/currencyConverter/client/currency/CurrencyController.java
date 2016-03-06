package com.zooplus.challenge.currencyConverter.client.currency;

import com.zooplus.challenge.currencyConverter.service.currency.CurrencyQuery;
import com.zooplus.challenge.currencyConverter.service.currency.currencies.CurrenciesService;
import com.zooplus.challenge.currencyConverter.service.currency.exchange.CurrencyExchangeCalculator;
import com.zooplus.challenge.currencyConverter.service.currency.history.CurrencyQueryHistoryReader;
import com.zooplus.challenge.currencyConverter.service.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/currency/")
public class CurrencyController {

    @Autowired
    private CurrencyExchangeCalculator currencyExchangeCalculator;

    @Autowired
    private CurrenciesService currenciesService;

    @Autowired
    private CurrencyQueryHistoryReader currencyQueryHistoryReader;

    @Autowired
    private CurrencyExchangeFormValidator currencyExchangeFormValidator;

    @InitBinder("currencyExchangeForm")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(currencyExchangeFormValidator);
    }
    
    @RequestMapping(value = "/main.html", method = RequestMethod.GET)
    public ModelAndView mainPage(User user) {
        ModelAndView mav = getModel(user);
        return mav;
    }

    @RequestMapping(value = "/main.html", method = RequestMethod.POST)
    public ModelAndView calculateExchange(User user, @Valid CurrencyExchangeForm currencyExchangeForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mav = getModel(user);
            mav.addObject("currencyExchangeForm", currencyExchangeForm);
            return mav;
        }
        CurrencyQuery result = currencyExchangeCalculator.calculateExchange(user,
                currencyExchangeForm.getSourceCurrency(),
                currencyExchangeForm.getTargetCurrency(),
                new BigDecimal(currencyExchangeForm.getValue()));
        ModelAndView mav = getModel(user);
        mav.addObject("result", result);
        return mav;
    }
    
    private ModelAndView getModel(User user) {
        ModelAndView mav = new ModelAndView("currency/main");
        mav.addObject("username", user.getUsername());
        mav.addObject("currencies", currenciesService.getSupportedCurrencies());
        List<CurrencyQuery> recentCurrencyQueries = currencyQueryHistoryReader.getRecentCurrencyQueries(user);
        mav.addObject("recentCurrencyQueries", recentCurrencyQueries);
        CurrencyExchangeForm newExchangeForm = new CurrencyExchangeForm();
        if (!recentCurrencyQueries.isEmpty()) {
            CurrencyQuery latestQuery = recentCurrencyQueries.get(0);
            newExchangeForm.setSourceCurrency(latestQuery.getSourceCurrency().getId());
            newExchangeForm.setTargetCurrency(latestQuery.getTargetCurrency().getId());
        }
        mav.addObject("currencyExchangeForm", newExchangeForm);
        return mav;
    }

}
