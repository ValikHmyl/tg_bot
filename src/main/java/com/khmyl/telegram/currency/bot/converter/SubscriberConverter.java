package com.khmyl.telegram.currency.bot.converter;

import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import com.khmyl.telegram.currency.bot.model.entity.Subscriber;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SubscriberConverter {

   public static Subscriber convert(SubscriberDto dto) {
      return Subscriber.builder()
                       .id(dto.getId())
                       .chatId(dto.getChatId())
                       .name(dto.getName())
                       .build();
   }

   public static List<Subscriber> convertDtos(Collection<SubscriberDto> dtos) {
      return dtos.stream().map(SubscriberConverter::convert).collect(Collectors.toList());
   }

   public static SubscriberDto convert(Subscriber dto) {
      return SubscriberDto.builder()
                       .id(dto.getId())
                       .chatId(dto.getChatId())
                       .name(dto.getName())
                       .build();
   }

   public static List<SubscriberDto> convertEntities(Collection<Subscriber> entities) {
      return entities.stream().map(SubscriberConverter::convert).collect(Collectors.toList());
   }

   public static SubscriberDto convert(Message message) {
      return SubscriberDto.builder()
                          .name(message.getFrom().getFirstName())
                          .id(message.getFrom().getId())
                          .chatId(message.getChatId())
                          .build();
   }
}
