package com.jitendra.async_impl.Utils;

import java.io.IOException;

import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.stereotype.Component;

import com.jitendra.async_impl.Service.RateLimitBucket4jService;

import io.github.bucket4j.Bucket;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitFilter implements Filter {

    private final RateLimitBucket4jService rateLimitBucket4jService;

    public RateLimitFilter(RateLimitBucket4jService rateLimitBucket4jService) {
        this.rateLimitBucket4jService = rateLimitBucket4jService;
    }


   // This filter intercepts incoming requests and applies rate limiting based on the client's IP address.
   // If the request exceeds the allowed rate, it responds with a 429 Too Many Requests
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        String clientIp = getClientIp(request);
        Bucket bucket = rateLimitBucket4jService.resolveBucket(clientIp);

        if(bucket.tryConsume(1)){
            chain.doFilter(req, res);   // Proceed with the request
        }
        else{
            response.setStatus(429);  // Too Many Requests
            response.getWriter().write("Rate limit exceeded. Please try again later.");
        }
        
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        System.out.println("Client IP: " + ip );
        return ip;
    }
    

}
