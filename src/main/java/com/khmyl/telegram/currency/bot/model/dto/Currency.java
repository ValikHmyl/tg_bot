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

   USD("USD", 431L), EUR("EUR", 451L), RUB("RUB", 456L), BYN("BYN", -1L);

   private final String code;
   private final Long id;//todo remove?

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
