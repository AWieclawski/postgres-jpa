package edu.awieclawski.postgresjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 
 * edu.awieclawski.postgresjpa.PostgresJpaApplication
 * 
 * @author AWieclawski
 * 
 * content optimized for WAR deployment from only one class.
 *
 */
@SpringBootApplication
public class PostgresJpaApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(PostgresJpaApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PostgresJpaApplication.class);
    }

}
