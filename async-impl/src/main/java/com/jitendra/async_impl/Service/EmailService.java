package com.jitendra.async_impl.Service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@Service
public class EmailService {

    @Async
    public void sendEmail(String email, String subject, String body) {
        try {
            // Simulate a delay for sending email
            Thread.sleep(4000); // Simulating network delay
             // Simulate sending an email
        System.out.println("Thead Name--> " +Thread.currentThread().getName());
        System.out.println("Sending email to: " + email);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Email sending interrupted: " + e.getMessage());
        }
       
    }

    @RateLimiter(name ="EmailRateLimiter",fallbackMethod = "emailFallBackMethod")
    public String emailTest(){
        return "Email Send Successful";
    }

    public String emailFallBackMethod(RequestNotPermitted e){
        return "Email Service limit exceeded, please try again later.";
    }

}
