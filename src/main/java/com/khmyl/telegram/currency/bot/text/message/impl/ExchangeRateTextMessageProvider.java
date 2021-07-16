package com.khmyl.telegram.currency.bot.text.message.impl;

import com.khmyl.telegram.currency.bot.text.message.TextMessageProvider;
import com.khmyl.telegram.currency.bot.model.dto.ExchangeRate;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.khmyl.telegram.currency.bot.model.dto.Currency.BYN;

@Component
public class ExchangeRateTextMessageProvider implements TextMessageProvider<ExchangeRate> {

   @Override
   public String getTextMessage(ExchangeRate exchangeRate) {
      return String.format("On %s %s NBRB rate is <b>%d %s = %s %s</b>", LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")), exchangeRate.getDate(), exchangeRate.getAmount(), exchangeRate.getCode(), exchangeRate.getRate(), BYN.getCode());
   }

}
