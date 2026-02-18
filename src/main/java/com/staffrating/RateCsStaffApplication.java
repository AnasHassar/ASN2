package com.staffrating;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;

@SpringBootApplication
public class RateCsStaffApplication {

    public static void main(String[] args) {
        String databaseUrl = System.getenv("DATABASE_URL");
        if (databaseUrl == null || databaseUrl.isEmpty()) {
            databaseUrl = System.getenv("INTERNAL_DATABASE_URL");
        }
        if (databaseUrl == null || databaseUrl.isEmpty()) {
            databaseUrl = System.getenv("EXTERNAL_DATABASE_URL");
        }

        if (databaseUrl != null && !databaseUrl.isEmpty() && databaseUrl.startsWith("postgresql://")) {
            try {
                URI dbUri = new URI(databaseUrl);
                String[] userInfo = dbUri.getUserInfo().split(":");
                String username = userInfo[0];
                String password = userInfo[1];
                int port = dbUri.getPort() == -1 ? 5432 : dbUri.getPort();
                String jdbcUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + port
                        + dbUri.getPath() + "?sslmode=require";

                System.setProperty("spring.datasource.url", jdbcUrl);
                System.setProperty("spring.datasource.username", username);
                System.setProperty("spring.datasource.password", password);
                System.setProperty("spring.datasource.driver-class-name", "org.postgresql.Driver");

                System.out.println("Configured PostgreSQL from DATABASE_URL: " + dbUri.getHost() + ":" + port);
            } catch (Exception e) {
                System.err.println("Failed to parse DATABASE_URL: " + e.getMessage());
            }
        }

        SpringApplication.run(RateCsStaffApplication.class, args);
    }
}
