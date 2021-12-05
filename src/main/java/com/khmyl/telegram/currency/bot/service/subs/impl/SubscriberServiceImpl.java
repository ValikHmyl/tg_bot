package com.khmyl.telegram.currency.bot.service.subs.impl;

import com.khmyl.telegram.currency.bot.converter.SubscriberConverter;
import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import com.khmyl.telegram.currency.bot.model.entity.Subscriber;
import com.khmyl.telegram.currency.bot.quartz.scheduler.SubscriberScheduler;
import com.khmyl.telegram.currency.bot.repository.SubscriberRepository;
import com.khmyl.telegram.currency.bot.service.subs.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class SubscriberServiceImpl implements SubscriberService {

   private static final String CACHE_NAME = "subscribers";

   @Autowired
   private SubscriberRepository subscriberRepository;

   @Autowired
   private SubscriberScheduler subscriberScheduler;

   @Override
   @CachePut(cacheNames = CACHE_NAME, key = "#dto.id")
   public SubscriberDto add(SubscriberDto dto) {
      if (!subscriberRepository.existsById(dto.getId())) {
         Subscriber entity = SubscriberConverter.convert(dto);
         subscriberRepository.save(entity);
         return SubscriberConverter.convert(entity);
      }
      return dto;
   }

   @Override
   @Cacheable(cacheNames = CACHE_NAME, key = "#root.methodName")
   public List<SubscriberDto> getAll() {
      return SubscriberConverter.convertEntities(subscriberRepository.findAll());
   }

   @Override
   @Cacheable(cacheNames = CACHE_NAME, key = "#id")
   public Optional<SubscriberDto> getById(Long id) {
      return subscriberRepository.findById(id).map(SubscriberConverter::convert);
   }

   @Override
   @CacheEvict(cacheNames = CACHE_NAME, key = "#id")
   public void remove(Long id) {
      if (Objects.nonNull(id)) {
         getById(id).ifPresent(sub -> {
            subscriberScheduler.deleteSubJobs(sub);
            subscriberRepository.deleteById(id);
         });
      }
   }

}
