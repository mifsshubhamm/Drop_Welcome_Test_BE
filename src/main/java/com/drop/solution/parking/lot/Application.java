package com.drop.solution.parking.lot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point for the Spring Boot application.
 *
 * This class contains the main method which starts the application.
 * It is annotated with {@link SpringBootApplication}, enabling
 * auto-configuration and component scanning in the Spring context.
 */
@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    /**
     * The main method that serves as the entry point for the application.
     *
     * @param args command-line arguments passed during application startup
     */
    public static void main(String[] args) {
        logger.info("Starting the Spring Boot application..."); // Log application start
        SpringApplication.run(Application.class, args);
        logger.info("Spring Boot application started successfully."); // Log successful start
    }
}
