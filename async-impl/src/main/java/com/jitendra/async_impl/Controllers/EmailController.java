package com.jitendra.async_impl.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jitendra.async_impl.Service.EmailService;
@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

     @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam(defaultValue = "abc@email.com") String email) {
        // Simulate user registration logic here

        // Trigger async email sending
        emailService.sendEmail(email, "Welcome!", "Thanks for registering with us!");

        return ResponseEntity.ok("User registered. Confirmation email is being sent.");
    }

    @GetMapping("/test")
    public String helloString(){
        return emailService.emailTest();
    }

    // Here we are applying rate limit for global endpoint by using doFilter method of RateLimitFilter class
    //If you do not want to apply global rate limit then remove the logic from doFilter method of RateLimitFilter class
    //and write it seperately
    @GetMapping("/testRateLimitBucket4j")
    public String testRateLimitBucket4j() {        
        return "Rate limit bucket4j is working fine";
    }

    



}
