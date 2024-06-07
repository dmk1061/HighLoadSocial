package org.otus.social.counter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.otus.social.counter", "org.otus.social.lib"})
public class CounterService {
    public static void main(String[] args) {

        SpringApplication.run(CounterService.class, args);
    }
}