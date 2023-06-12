package com.khmyl.telegram.currency.bot.model.dto.exchangerateapi;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class UnixDateDeserializer extends JsonDeserializer<LocalDate> {

   @Override
   public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
      String timestamp = jp.getText().trim();
      try {
         return LocalDate.ofInstant(Instant.ofEpochSecond(Long.parseLong(timestamp)), ZoneId.systemDefault());
      } catch (NumberFormatException e) {
         return LocalDate.now();
      }
   }
}
