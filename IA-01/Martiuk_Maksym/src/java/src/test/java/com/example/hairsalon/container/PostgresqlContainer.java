package com.example.hairsalon.container;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public class PostgresqlContainer {

    @Container
    private static final PostgreSQLContainer<?> CONTAINER = new PostgreSQLContainer<>("postgres")
            .withDatabaseName("test_barbershop");

    public static PostgreSQLContainer<?> getContainer() {
        return CONTAINER;
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + CONTAINER.getJdbcUrl(),
                    "spring.datasource.username=" + CONTAINER.getUsername(),
                    "spring.datasource.password=" + CONTAINER.getPassword()
            ).applyTo(applicationContext);
        }

    }

}
