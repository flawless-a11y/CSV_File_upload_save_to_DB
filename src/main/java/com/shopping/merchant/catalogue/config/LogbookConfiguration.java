package com.shopping.merchant.catalogue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.Logbook;

import static org.zalando.logbook.Conditions.*;

@Configuration
public class LogbookConfiguration {

    @Bean
    public Logbook logbook() {
        Logbook logbook = Logbook.builder()
                .condition(exclude(
                        requestTo("/health"),
                        requestTo("/admin/**"),
                        contentType("application/octet-stream"),
                        header("X-Secret", o -> false)))
                .build();
        return logbook;
    }
}