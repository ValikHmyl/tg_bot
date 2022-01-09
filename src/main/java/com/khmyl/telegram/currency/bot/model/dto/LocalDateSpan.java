package com.khmyl.telegram.currency.bot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor(staticName = "of")
public class LocalDateSpan {

   private LocalDate startDate;
   private LocalDate endDate;

   @Override
   public String toString() {
      return startDate + " - " + endDate;
   }
}
