package com.khmyl.telegram.currency.bot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SubscriberDto {

   private Long id;
   private Long chatId;
   private String name;
   //settings? - time or period? currencies?
}
