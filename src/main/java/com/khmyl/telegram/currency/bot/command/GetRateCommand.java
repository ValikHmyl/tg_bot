package com.khmyl.telegram.currency.bot.command;

import com.khmyl.telegram.currency.bot.model.dto.Currency;
import com.khmyl.telegram.currency.bot.model.dto.ExchangeRate;
import com.khmyl.telegram.currency.bot.service.currency.CurrencyRateService;
import com.khmyl.telegram.currency.bot.message.text.TextMessageProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalDate;

import static com.khmyl.telegram.currency.bot.message.text.TextMessageConstants.GET_RATE_COMMAND_MESSAGE_KEY;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Slf4j
@Component
@Scope(SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class GetRateCommand implements Command {

   private final Long chatId;
   private final Currency currency;
   private final LocalDate date;

   @Autowired
   private CurrencyRateService currencyRateService;

   @Autowired
   private TextMessageProvider textMessageProvider;

   @Override
   public SendMessage execute() {
      ExchangeRate exchangeRate = currencyRateService.getRate(currency.getCode(), date);
      return SendMessage.builder()
                        .chatId(chatId.toString())
                        .text(textMessageProvider.getTextMessage(GET_RATE_COMMAND_MESSAGE_KEY, exchangeRate))
                        .parseMode(ParseMode.HTML)
                        .build();
   }
}
