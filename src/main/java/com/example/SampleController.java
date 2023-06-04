package com.example;

import io.github.resilience4j.ratelimiter.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.TooManyListenersException;

@RestController
public class SampleController {

    @Qualifier("rateLimiter1")
    @Autowired
    private  RateLimiter rateLimiter;

    public SampleController() {
//        this.rateLimiter = rateLimiter;
    }

    @Autowired
    private com.example.services.ExternalApiCalls externalApiCalls;
    @GetMapping(value = "/")
    public ResponseEntity<String> Sample() {
        // Acquire a permit from the rate limiter

        System.out.println(rateLimiter.getRateLimiterConfig());
        System.out.println(rateLimiter.getMetrics().getNumberOfWaitingThreads());
//        System.out.println(rateLimiter.getRateLimiterConfig());
//        System.out.println(rateLimiter.getRateLimiterConfig());
//        System.out.println(rateLimiter.getRateLimiterConfig());
        if (rateLimiter.acquirePermission()) {
            return new ResponseEntity<>("Sample data", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Sample data", HttpStatus.TOO_MANY_REQUESTS);
        }
    }



    @GetMapping(value = "/api")
    public ResponseEntity<String> Sample1() throws InterruptedException {
        // Acquire a permit from the rate limiter

        System.out.println(rateLimiter.getRateLimiterConfig());
        System.out.println(rateLimiter.getMetrics().getNumberOfWaitingThreads());
//        System.out.println(rateLimiter.getRateLimiterConfig());
//        System.out.println(rateLimiter.getRateLimiterConfig());
//        System.out.println(rateLimiter.getRateLimiterConfig());
        try{
          String s=  externalApiCalls.ExternalAPI();
            return new ResponseEntity<>(s, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.TOO_MANY_REQUESTS);
        }
//        externalApiCalls.ExternalAPI();
//        if (rateLimiter.acquirePermission()) {
//            return new ResponseEntity<>("Sample data", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Sample data", HttpStatus.TOO_MANY_REQUESTS);
//        }
    }


//    @GetMapping(value = "/test")
//    RateLimiter(name = "myRateLimiter",fallbackMethod = "fallback")
//    public ResponseEntity<String> test() {
//        // Acquire a permit from the rate limiter
//        return new ResponseEntity<>("Sample data", HttpStatus.OK);
//    }

    public ResponseEntity<String> fallback(Exception e) {
        return new ResponseEntity<>("Error Data", HttpStatus.TOO_MANY_REQUESTS);
    }
}
