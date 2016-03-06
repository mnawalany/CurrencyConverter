package com.zooplus.challenge.currencyConverter.client.currency;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

import static org.springframework.util.StringUtils.isEmpty;

@Component
public class CurrencyExchangeFormValidator implements Validator {

    public static final BigDecimal MAX_VALUE = new BigDecimal(999999999);
    public static final BigDecimal MIN_VALUE = BigDecimal.ZERO;

    @Override
    public boolean supports(Class<?> clazz) {
        return CurrencyExchangeForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CurrencyExchangeForm currencyExchangeForm = (CurrencyExchangeForm) target;
        if (isEmpty(currencyExchangeForm.getValue())) {
            errors.rejectValue("value", "currencyExchange.fields.value.empty", "Please provide the value");
        } else {
            try {
                BigDecimal bigDecimal = new BigDecimal(currencyExchangeForm.getValue());
                if (bigDecimal.compareTo(MIN_VALUE) <= 0) {
                    errors.rejectValue("value", "currencyExchange.fields.value.tooSmall", String.format("Value cannot be smaller or equal to %s", MIN_VALUE));
                } else if (bigDecimal.compareTo(MAX_VALUE) > 0) {
                    errors.rejectValue("value", "currencyExchange.fields.value.tooBig", String.format("Value cannot exeed %s", MAX_VALUE));
                }
            } catch (NumberFormatException e) {
                errors.rejectValue("value", "currencyExchange.fields.value.invalid", "Provided value is not a number");
            }
        }
        if (currencyExchangeForm.getSourceCurrency().equals(currencyExchangeForm.getTargetCurrency())) {
            errors.reject("currencyExchange.currencies.same", "Currencies cannot be the same");
        }
    }
}
