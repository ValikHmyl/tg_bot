package com.khmyl.telegram.currency.bot.service.subs;

import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;

import java.util.List;
import java.util.Optional;

public interface SubscriberService {

   void add(SubscriberDto subscriber);

   List<SubscriberDto> getAll();

   Optional<SubscriberDto> getById(Long id);

   void remove(Long id);

   boolean exist(Long id);
}
