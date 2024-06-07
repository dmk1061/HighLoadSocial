package org.otus.social.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableScheduling
@EnableWebSecurity
@EnableFeignClients
@ComponentScan(basePackages = {"org.otus.social.main", "org.otus.social.lib"})
public class MainService {

    public static void main(String[] args) {
        SpringApplication.run(MainService.class, args);
    }
}
