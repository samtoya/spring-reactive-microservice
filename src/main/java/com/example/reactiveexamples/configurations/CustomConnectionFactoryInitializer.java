package com.example.reactiveexamples.configurations;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
public class CustomConnectionFactoryInitializer {
    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);

        CompositeDatabasePopulator populate = new CompositeDatabasePopulator();
        populate.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schemas/data.sql")));
        initializer.setDatabasePopulator(populate);
        return initializer;
    }
}
