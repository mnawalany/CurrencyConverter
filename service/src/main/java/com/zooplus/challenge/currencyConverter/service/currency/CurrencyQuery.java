package com.zooplus.challenge.currencyConverter.service.currency;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CurrencyQuery {

    private final LocalDateTime date;

    private final Currency sourceCurrency;

    private final BigDecimal sourceValue;

    private final Currency targetCurrency;

    private final BigDecimal targetValue;

    public CurrencyQuery(LocalDateTime date, Currency sourceCurrency, BigDecimal sourceValue, Currency targetCurrency, BigDecimal targetValue) {
        this.date = date;
        this.sourceCurrency = sourceCurrency;
        this.sourceValue = sourceValue;
        this.targetCurrency = targetCurrency;
        this.targetValue = targetValue;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Currency getSourceCurrency() {
        return sourceCurrency;
    }

    public BigDecimal getSourceValue() {
        return sourceValue;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public BigDecimal getTargetValue() {
        return targetValue;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
