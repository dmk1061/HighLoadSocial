package org.otus.social.dialog;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricConfiguration {

    @Bean
    public Counter requestCounter(MeterRegistry registry) {
        return Counter.builder("main_service_requests_total")
                .description("Total number of requests")
                .register(registry);
    }

    @Bean
    public Counter errorCounter(MeterRegistry registry) {
        return Counter.builder("main_service_errors_total")
                .description("Total number of errors")
                .register(registry);
    }

    @Bean
    public Timer requestTimer(MeterRegistry registry) {
        return Timer.builder("main_service_request_duration_seconds")
                .description("Request duration in seconds")
                .register(registry);
    }
}