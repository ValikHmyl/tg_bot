package com.khmyl.telegram.currency.bot.message.text.impl.template;

import com.khmyl.telegram.currency.bot.message.text.MessageTemplate;
import com.khmyl.telegram.currency.bot.model.dto.ExchangeRate;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static com.khmyl.telegram.currency.bot.model.dto.Currency.BYN;
import static com.khmyl.telegram.currency.bot.message.text.TextMessageConstants.DATE_TIME_KEY;
import static com.khmyl.telegram.currency.bot.message.text.TextMessageConstants.SOURCE_AMOUNT_KEY;
import static com.khmyl.telegram.currency.bot.message.text.TextMessageConstants.SOURCE_CURRENCY_KEY;
import static com.khmyl.telegram.currency.bot.message.text.TextMessageConstants.TARGET_AMOUNT_KEY;
import static com.khmyl.telegram.currency.bot.message.text.TextMessageConstants.TARGET_CURRENCY_KEY;

@NoArgsConstructor
@AllArgsConstructor
public class GetRateMessageTemplate implements MessageTemplate {

   private ExchangeRate exchangeRate;

   @Override
   public Map<String, Object> getArgs() {
      return Map.of(DATE_TIME_KEY, LocalDateTime.of(exchangeRate.getDate(), LocalTime.now()).format(DateTimeFormatter.ofPattern("HH:mm:ss yyyy-MM-dd")),
              TARGET_AMOUNT_KEY, exchangeRate.getAmount(),
              TARGET_CURRENCY_KEY, exchangeRate.getCode(),
              SOURCE_AMOUNT_KEY, exchangeRate.getRate(),
              SOURCE_CURRENCY_KEY, BYN.getCode());
   }

   @Override
   public String getMessageKey() {
      return "message.get_rate.command";
   }
}
