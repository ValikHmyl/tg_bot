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

   USD("USD"), EUR("EUR"), RUB("RUB"), BYN("BYN");

   private final String code;

   public static Currency ofCode(String code) {
      return Arrays.stream(Currency.values())
              .filter(currency -> code.equalsIgnoreCase(currency.getCode()))
              .findFirst()
              .orElseThrow(() -> new IllegalArgumentException("Unsupported currency code: " + code));
   }

   public static List<Currency> getCurrenciesForRate() {
      return Arrays.stream(Currency.values()).filter(currency -> !currency.getCode().equals(BYN.getCode())).collect(Collectors.toList());
   }
}
