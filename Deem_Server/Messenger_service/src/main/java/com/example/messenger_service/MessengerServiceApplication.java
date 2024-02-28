package com.example.messenger_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
public class MessengerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessengerServiceApplication.class, args);
    }

}
