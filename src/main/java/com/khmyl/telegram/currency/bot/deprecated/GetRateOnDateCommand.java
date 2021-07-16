package com.khmyl.telegram.currency.bot.deprecated;

import com.khmyl.telegram.currency.bot.text.message.TextMessageProvider;
import com.khmyl.telegram.currency.bot.model.dto.Currency;
import com.khmyl.telegram.currency.bot.model.dto.ExchangeRate;
import com.khmyl.telegram.currency.bot.service.currency.CurrencyRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.util.Arrays;

import static com.khmyl.telegram.currency.bot.model.dto.Currency.EUR;
import static com.khmyl.telegram.currency.bot.model.dto.Currency.RUB;
import static com.khmyl.telegram.currency.bot.model.dto.Currency.USD;

@Slf4j
@Deprecated
//@Component
public class GetRateOnDateCommand implements IBotCommand {

   @Value("${command.rate.name}")
   private String name;
   @Value("${command.rate.description}")
   private String description;

   @Autowired
   private CurrencyRateService currencyRateService;

   @Autowired
   private TextMessageProvider<ExchangeRate> textMessageProvider;

   @Override
   public String getCommandIdentifier() {
      return name;
   }

   @Override
   public String getDescription() {
      return description;
   }

   @Override
   public void processMessage(AbsSender absSender, Message message, String[] arguments) {
      Currency currency = getCurrency(arguments);
      LocalDate date = getDate(arguments);
      ExchangeRate exchangeRate = currencyRateService.getRate(currency.getCode(), date);
      try {
         absSender.execute(getResponse(message, exchangeRate));
      } catch (TelegramApiException e) {
         log.error("Unexpected telegram error", e);
      }
   }

   private BotApiMethod<?> getResponse(Message message, ExchangeRate exchangeRate) {
      String responseText = textMessageProvider.getTextMessage(exchangeRate);
      InlineKeyboardMarkup inlineKeyboardMarkup = buildKeyboard();
      BotApiMethod<?> response;
      if (message.getFrom().getIsBot()) {
          response = EditMessageText.builder()
                 .messageId(message.getMessageId())
                 .chatId(message.getChatId().toString())
                 .text(responseText)
                 .parseMode(ParseMode.HTML)
                 .replyMarkup(inlineKeyboardMarkup)
                 .build();
      } else {
          response = SendMessage.builder()
                 .chatId(message.getChatId().toString())
                 .text(responseText)
                 .parseMode(ParseMode.HTML)
                 .replyMarkup(inlineKeyboardMarkup)
                 .build();
      }
      return response;
   }

   private InlineKeyboardMarkup buildKeyboard() {
      //delegate
      return InlineKeyboardMarkup.builder()
              .keyboardRow(Arrays.asList(getCurrencyButton(USD), getCurrencyButton(EUR), getCurrencyButton(RUB)))
              .build();
   }

   private InlineKeyboardButton getCurrencyButton(Currency currency) {
      return InlineKeyboardButton.builder()
              .text(currency.getCode())
              .callbackData(BotCommand.COMMAND_INIT_CHARACTER + getCommandIdentifier() + " " + currency.getCode())
              .build();
   }

   private LocalDate getDate(String[] arguments) {
      LocalDate date = LocalDate.now();
      if (arguments.length > 1) {
         date = LocalDate.parse(arguments[1]);
      }
      return date;
   }

   private Currency getCurrency(String[] arguments) {
      Currency currency = USD;
      if (arguments.length != 0) {
         currency = Currency.ofCode(arguments[0]);
      }
      return currency;
   }
}
