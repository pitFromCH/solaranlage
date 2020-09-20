package com.pit.solarserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableRetry
@SpringBootApplication
@EnableScheduling
public class SolarserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolarserverApplication.class, args);
    }

}
