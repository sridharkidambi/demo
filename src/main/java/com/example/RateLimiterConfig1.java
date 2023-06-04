package com.example;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RateLimiterConfig1 {

    @Bean
    public RateLimiter rateLimiter1() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(1) // Maximum number of calls within a time period
                .limitRefreshPeriod(Duration.ofSeconds(10)) // Time period to refresh the limit
                .timeoutDuration(Duration.ofSeconds(2)) // Timeout for acquiring a permit
                .build();

        RateLimiterRegistry registry = RateLimiterRegistry.of(config);
        return registry.rateLimiter("myRateLimiter");
    }


    @Bean
    public RateLimiter rateLimiter2() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(2) // Maximum number of calls within a time period
                .limitRefreshPeriod(Duration.ofSeconds(10)) // Time period to refresh the limit
                .timeoutDuration(Duration.ofSeconds(2)) // Timeout for acquiring a permit
                .build();

        RateLimiterRegistry registry = RateLimiterRegistry.of(config);
        return  registry.rateLimiter("myRateLimiter2");
    }

    @Bean
    public RestTemplate restTemplate(){
        return  new RestTemplate();
    }
}
