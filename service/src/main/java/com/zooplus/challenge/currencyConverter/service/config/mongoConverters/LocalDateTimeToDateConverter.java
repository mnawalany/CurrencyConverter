package com.zooplus.challenge.currencyConverter.service.config.mongoConverters;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class LocalDateTimeToDateConverter implements Converter<LocalDateTime, Date> {

    @Override
    public Date convert(LocalDateTime source) {
        return source == null ? null : Date.from(source.atZone(ZoneId.systemDefault()).toInstant());
    }

}
