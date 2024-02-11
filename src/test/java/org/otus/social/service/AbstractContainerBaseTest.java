package org.otus.social.service;


import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class AbstractContainerBaseTest {
    static final PostgreSQLContainer POSTGRES_CONTAINER;


    static {
        POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:latest")
                .withUsername("postgres")
                .withPassword("pass")
                .withDatabaseName("postgres");
        POSTGRES_CONTAINER.start();
    }


    @DynamicPropertySource
    public static void dynamicPropertySource(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", ()->POSTGRES_CONTAINER.getJdbcUrl());
        dynamicPropertyRegistry.add("spring.datasource.username",()-> POSTGRES_CONTAINER.getUsername());
        dynamicPropertyRegistry.add("spring.datasource.password", ()->POSTGRES_CONTAINER.getPassword());
    }

}
