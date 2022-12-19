package com.example.hairsalon.container;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles(profiles = {"test", "s3-minio"})
@ContextConfiguration(initializers = {
        PostgresqlContainer.Initializer.class
})
public class IntegrationTestBase {

    @BeforeAll
    static void init() {
        PostgresqlContainer.getContainer().start();
    }

}
