package com.shopping.merchant.catalogue.config.logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.StreamHttpLogWriter;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import static org.zalando.logbook.Conditions.*;

@Configuration
public class LogbookConfiguration {
    @Value("${logFilePath}")
    private String logFilePath;

    @Bean
    public Logbook logbook() throws FileNotFoundException {
        PrintStream output;
        output = new PrintStream(logFilePath);
        Logbook logbook = Logbook.builder()
                .condition(exclude(
                        requestTo("/health"),
                        requestTo("/admin/**"),
                        requestTo("/swagger-ui/**"),
                        requestTo("/v3/api-docs/**"),
                        contentType("application/octet-stream"),
                        header("X-Secret", o -> false)))
                .sink(new CommonsLogFormatter(
                        new StreamHttpLogWriter(output)))
                .build();

        return logbook;
    }
}