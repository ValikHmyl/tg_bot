package com.khmyl.telegram.currency.bot.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Currency {

   USD("USD", 145L), EUR("EUR", 292L), RUB("RUB", 298L), BYN("BYN", -1L);

   private final String code;
   private final Long id;

   public static Currency ofCode(String code) {
      return Arrays.stream(Currency.values())
              .filter(currency -> code.equalsIgnoreCase(currency.getCode()))
              .findFirst()
              .orElseThrow(() -> new IllegalArgumentException("Unsupported currency code: " + code));
   }

   public static List<Currency> getCurrenciesForRate() {
      return Arrays.stream(Currency.values()).filter(currency -> currency.getId() > 0).collect(Collectors.toList());
   }
}
