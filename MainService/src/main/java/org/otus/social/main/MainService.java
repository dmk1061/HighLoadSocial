package org.otus.social.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableScheduling
@EnableWebSecurity
@EnableJpaRepositories(basePackages = "org.otus.soical.maib.repository")
@ComponentScan(basePackages = {"org.otus.social.main", "org.otus.social.lib"})
public class MainService {

    public static void main(String[] args) {
        SpringApplication.run(MainService.class, args);
    }
}
