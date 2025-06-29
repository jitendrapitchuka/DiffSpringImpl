package com.jitendra.async_impl.Config;


import java.time.Duration;
import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.distributed.proxy.ClientSideConfig;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;



@Configuration
public class RateLimiterBucket4jConfig {

/**
 * Configures the bucket to expire from Redis if it remains unused
 * for 10 minutes after being fully refilled.
 *
 * This helps free up Redis memory by automatically removing
 * idle rate-limiting buckets (e.g. for inactive IPs).
 */
ClientSideConfig clientSideConfig = ClientSideConfig.getDefault().withExpirationAfterWriteStrategy(
    ExpirationAfterWriteStrategy.basedOnTimeForRefillingBucketUpToMax(Duration.ofMinutes(10))
);
 @Bean
 public RedisClient redisClient(){
    return RedisClient.create("redis://localhost:6379");
 }

 /*
  * ProxyManager is the core component of Bucket4j that manages the distributed state.
  * It is like a manager that handles the creation of buckets for new clients, retrieving existing buckets, synchronizing bucket state across the multiple application instances.
  */

 @Bean
 public ProxyManager<String> proxyManager(RedisClient redisClient){
    StatefulRedisConnection<String,byte[]>connection= redisClient.connect(RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE));
    return LettuceBasedProxyManager.builderFor(connection).withClientSideConfig(clientSideConfig).
    build();
 } 

 /*
  * bucketConfiguration method defines the rules for buckets,
  *basically it defines the blueprint for bucket(capacity, refill rate, etc).
  */
    @Bean
    public Supplier<BucketConfiguration> bucketConfiguration(){
        return ()->BucketConfiguration.builder().addLimit(Bandwidth.simple(5,Duration.ofMinutes(1))).build();
    }


}
