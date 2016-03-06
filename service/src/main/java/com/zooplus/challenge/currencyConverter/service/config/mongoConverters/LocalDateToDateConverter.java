package com.zooplus.challenge.currencyConverter.service.config.mongoConverters;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class LocalDateToDateConverter implements Converter<LocalDate, Date> {

    @Override
    public Date convert(LocalDate source) {
        return source == null ? null : Date.from(source.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

}
