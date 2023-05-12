package com.khmyl.telegram.currency.bot.command.impl;

import com.khmyl.telegram.currency.bot.command.Command;
import com.khmyl.telegram.currency.bot.command.Response;
import com.khmyl.telegram.currency.bot.command.impl.response.decorator.MessageWithDefaultKeyboard;
import com.khmyl.telegram.currency.bot.command.impl.response.MessageResponse;
import com.khmyl.telegram.currency.bot.message.text.TextMessageProvider;
import com.khmyl.telegram.currency.bot.message.text.impl.template.GetRateMessageTemplate;
import com.khmyl.telegram.currency.bot.model.dto.Currency;
import com.khmyl.telegram.currency.bot.model.dto.ExchangeRate;
import com.khmyl.telegram.currency.bot.service.currency.CurrencyRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalDate;

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
   public Response execute() {
      ExchangeRate exchangeRate = currencyRateService.getRate(currency.getCode(), date);
      SendMessage message = MessageWithDefaultKeyboard.toBuilder()
                                                      .chatId(chatId.toString())
                                                      .text(textMessageProvider.getTextMessage(new GetRateMessageTemplate(exchangeRate)))
                                                      .build();
      return MessageResponse.of(message);
   }
}
