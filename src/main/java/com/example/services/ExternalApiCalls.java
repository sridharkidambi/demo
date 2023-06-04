package com.example.services;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
//import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalApiCalls {

    @Autowired
    private  RestTemplate restTemplate;

    @Autowired
    @Qualifier("rateLimiter2")
    private RateLimiter rateLimiter;


//    @io.github.resilience4j.ratelimiter.annotation.RateLimiter(name="myRateLimiter2" , fallbackMethod = "getFallbackStocks")
    public  String ExternalAPI() throws InterruptedException {
        Integer iCount=0;
        try {

           if(rateLimiter.acquirePermission()) {
               Thread.sleep(2000);
//            System.out.println("time making the call: " + LocalDateTime.now());
            String s = restTemplate.getForObject("https://yahoo.com", String.class);
               System.out.println("normal processing1");
               return s;
           }else{
               throw  new HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS,"too many SK");
           }
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw  e;
        }
    }
//    @RateLimiter(name="stockService", fallbackMethod = "getFallbackStocks")
//    public static  String ExternStaticalCall() throws InterruptedException {
//        Thread.sleep(2000);
//        String s= restTemplate.getForObject("https://yahoo.com",String.class);
//        return s;
//    }


    public String getFallbackStocks(String request, InterruptedException rnp) {
        // fetch results from the cache
        System.out.print("error captured--SK");
        return "SK fallbackcalls";
    }
}
