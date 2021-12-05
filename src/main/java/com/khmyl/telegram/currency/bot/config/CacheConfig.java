package com.khmyl.telegram.currency.bot.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

   @Value("${cache.expire.hours:12}")
   private int expireAfter;
   @Value("${cache.max.size:50}")
   private int cacheMaxSize;

   @Bean
   public Caffeine<Object, Object> caffeine() {
      return Caffeine.newBuilder().maximumSize(cacheMaxSize).expireAfterWrite(expireAfter, TimeUnit.HOURS);
   }

   @Bean
   public CacheManager cacheManager() {
      CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
      caffeineCacheManager.setCaffeine(caffeine());
      return caffeineCacheManager;
   }
}
