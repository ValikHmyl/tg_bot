package com.khmyl.telegram.currency.bot.service.subs.impl;

import com.khmyl.telegram.currency.bot.converter.SubscriberConverter;
import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import com.khmyl.telegram.currency.bot.quartz.scheduler.SubscriberScheduler;
import com.khmyl.telegram.currency.bot.repository.SubscriberRepository;
import com.khmyl.telegram.currency.bot.service.subs.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class SubscriberServiceImpl implements SubscriberService {

   @Autowired
   private SubscriberRepository subscriberRepository;

   @Autowired
   private SubscriberScheduler subscriberScheduler;

   @Override
   public void add(SubscriberDto dto) {
      if (!subscriberRepository.existsById(dto.getId())) {
         subscriberRepository.save(SubscriberConverter.convert(dto));
      }
   }

   @Override
   public List<SubscriberDto> getAll() {
      return SubscriberConverter.convertEntities(subscriberRepository.findAll());
   }

   @Override
   public Optional<SubscriberDto> getById(Long id) {
      return subscriberRepository.findById(id).map(SubscriberConverter::convert);
   }

   @Override
   public void remove(Long id) {
      if (Objects.nonNull(id)) {
         getById(id).ifPresent(sub -> {
            subscriberScheduler.deleteSubJobs(sub);
            subscriberRepository.deleteById(id);
         });
      }
   }

}
