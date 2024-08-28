package com.onlineBookStore.BookStoreApi.metrics;
import io.micrometer.core.instrument.MeterRegistry;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class CustomMetrices {
    @PostConstruct
    public void initCustomMetrics() {
        meterRegistry.counter("bookstore.custom.metric", "type", "example");

        meterRegistry.gauge("bookstore.books.count", () -> getBookCount());
    }
    private int getBookCount() {
        // Returbning the example Count
        return 42;
    }
}
