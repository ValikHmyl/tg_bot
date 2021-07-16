package com.khmyl.telegram.currency.bot.service.subs.impl;

import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import com.khmyl.telegram.currency.bot.service.subs.SubscriberService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@Service
public class InMemorySubscriberService implements SubscriberService {

   private final Map<Long, SubscriberDto> subs = new HashMap<>();

   @Override
   public void add(SubscriberDto subscriber) {
      subs.put(subscriber.getId(), subscriber);
   }

   @Override
   public List<SubscriberDto> getAll() {
      return new ArrayList<>(subs.values());
   }

   @Override
   public Optional<SubscriberDto> getById(Long id) {
      return Optional.of(subs.get(id));
   }

   @Override
   public void remove(Long id) {
      subs.remove(id);
   }

   @Override
   public boolean exist(Long id) {
      return subs.containsKey(id);
   }
}
