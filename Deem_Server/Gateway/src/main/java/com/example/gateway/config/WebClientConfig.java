package com.example.gateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    /*@Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(2, 10); // 2 запросов в секунду, 10 в минуту
    }

    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
        *//*return exchange -> {
            String key = exchange.getRequest().getHeaders().getFirst("Authorization");
            System.out.println("KEY RESOLVED: " + key);
            return Mono.just(key != null ? key : "anonymous");
        };*//*
    }*/

}
