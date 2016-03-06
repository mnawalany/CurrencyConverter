package com.zooplus.challenge.currencyConverter.service.config.mongoConverters;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateToLocalDateConverter implements Converter<Date, LocalDate> {

    @Override
    public LocalDate convert(Date source) {
        return source == null ? null : source.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
