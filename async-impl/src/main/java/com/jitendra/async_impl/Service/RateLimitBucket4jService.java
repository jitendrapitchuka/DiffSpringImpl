package com.jitendra.async_impl.Service;

import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.proxy.ProxyManager;

@Service
public class RateLimitBucket4jService {

    private final ProxyManager<String> proxyManager;
    private final Supplier<BucketConfiguration> bucketConfiguration;

    public RateLimitBucket4jService(ProxyManager<String> proxyManager, Supplier<BucketConfiguration> bucketConfiguration) {
        this.proxyManager = proxyManager;
        this.bucketConfiguration = bucketConfiguration;
    }

    public Bucket resolveBucket(String ipAddress){
        return proxyManager.builder().build(ipAddress, bucketConfiguration);
    }
    
}
