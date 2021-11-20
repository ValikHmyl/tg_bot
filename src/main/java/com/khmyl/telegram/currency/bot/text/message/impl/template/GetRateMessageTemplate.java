package com.khmyl.telegram.currency.bot.text.message.impl.template;

import com.khmyl.telegram.currency.bot.model.dto.ExchangeRate;
import com.khmyl.telegram.currency.bot.text.message.MessageTemplate;
import com.khmyl.telegram.currency.bot.text.message.TextMessageConstants;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static com.khmyl.telegram.currency.bot.model.dto.Currency.BYN;
import static com.khmyl.telegram.currency.bot.text.message.TextMessageConstants.DATE_TIME_KEY;
import static com.khmyl.telegram.currency.bot.text.message.TextMessageConstants.SOURCE_AMOUNT_KEY;
import static com.khmyl.telegram.currency.bot.text.message.TextMessageConstants.SOURCE_CURRENCY_KEY;
import static com.khmyl.telegram.currency.bot.text.message.TextMessageConstants.TARGET_AMOUNT_KEY;
import static com.khmyl.telegram.currency.bot.text.message.TextMessageConstants.TARGET_CURRENCY_KEY;

@Component(TextMessageConstants.GET_RATE_COMMAND_MESSAGE_KEY)
@Scope("prototype")
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
   public String getMessage() {
      //to properties and localize?
      return "On {date_time} NBRB rate is <b>{target_amount} {target_currency} = {source_amount} {source_currency}</b>";
   }
}
