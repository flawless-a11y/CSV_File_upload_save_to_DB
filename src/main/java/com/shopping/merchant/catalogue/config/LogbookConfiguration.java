package com.shopping.merchant.catalogue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.*;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import static org.zalando.logbook.Conditions.*;
@Configuration
public class LogbookConfiguration {

    @Bean
    public Logbook logbook() throws FileNotFoundException {
        PrintStream output;
        output= new PrintStream("/Users/prashant2.chauhan/Desktop/myapp.log");
        Logbook logbook = Logbook.builder()
                .condition(exclude(
                        requestTo("/health"),
                        requestTo("/admin/**"),
                        contentType("application/octet-stream"),
                        header("X-Secret", o -> false)))
                .sink(new CommonsLogFormatSink(
                new StreamHttpLogWriter(output)))
                .build();

        return logbook;
    }
}