package com.zooplus.challenge.currencyConverter.service.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.zooplus.challenge.currencyConverter.service")
public class ServiceConfiguration {

}
