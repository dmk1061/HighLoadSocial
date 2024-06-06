package org.otus.social.dialog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@SpringBootApplication
@EnableScheduling
@EnableWebSecurity
@ComponentScan(basePackages = {"org.otus.social.dialog", "org.otus.social.lib"})
public class DialogService {
    public static void main(String[] args) {

        SpringApplication.run(DialogService.class, args);
    }
}