package com.zooplus.challenge.currencyConverter.client.currency;

import org.junit.Test;
import org.springframework.validation.Errors;

import static io.codearte.catchexception.shade.mockito.Mockito.mock;
import static io.codearte.catchexception.shade.mockito.Mockito.verify;
import static io.codearte.catchexception.shade.mockito.Mockito.verifyNoMoreInteractions;

public class CurrencyExchangeFormValidatorTest {

    private CurrencyExchangeFormValidator validator = new CurrencyExchangeFormValidator();

    private Errors errors = mock(Errors.class);

    @Test
    public void shouldRejectWhenNoValue() {
        // Given
        CurrencyExchangeForm currencyExchangeForm = validForm();
        currencyExchangeForm.setValue("");

        // When
        validator.validate(currencyExchangeForm, errors);

        // Then
        verify(errors).rejectValue("value", "currencyExchange.fields.value.empty", "Please provide the value");
    }

    @Test
    public void shouldRejectWhenValueNotANumber() {
        // Given
        CurrencyExchangeForm currencyExchangeForm = validForm();
        currencyExchangeForm.setValue("not number");

        // When
        validator.validate(currencyExchangeForm, errors);

        // Then
        verify(errors).rejectValue("value", "currencyExchange.fields.value.invalid", "Provided value is not a number");
    }

    @Test
    public void shouldRejectSameCurrencies() {
        // Given
        CurrencyExchangeForm currencyExchangeForm = validForm();
        currencyExchangeForm.setSourceCurrency("A");
        currencyExchangeForm.setTargetCurrency("A");

        // When
        validator.validate(currencyExchangeForm, errors);

        // Then
        verify(errors).reject("currencyExchange.currencies.same", "Currencies cannot be the same");
    }

    @Test
    public void shouldAcceptValidForm() {
        // Given
        CurrencyExchangeForm currencyExchangeForm = validForm();

        // When
        validator.validate(currencyExchangeForm, errors);

        // Then
        verifyNoMoreInteractions(errors);
    }

    private CurrencyExchangeForm validForm() {
        CurrencyExchangeForm currencyExchangeForm = new CurrencyExchangeForm();
        currencyExchangeForm.setValue("11.22");
        currencyExchangeForm.setSourceCurrency("A");
        currencyExchangeForm.setTargetCurrency("B");
        return currencyExchangeForm;
    }

}