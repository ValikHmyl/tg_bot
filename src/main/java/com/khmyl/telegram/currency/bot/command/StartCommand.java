package com.khmyl.telegram.currency.bot.command;

import com.khmyl.telegram.currency.bot.converter.SubscriberConverter;
import com.khmyl.telegram.currency.bot.model.dto.Currency;
import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import com.khmyl.telegram.currency.bot.quartz.scheduler.SubscriberScheduler;
import com.khmyl.telegram.currency.bot.service.subs.SubscriberService;
import com.khmyl.telegram.currency.bot.message.text.TextMessageProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import static com.khmyl.telegram.currency.bot.message.text.TextMessageConstants.START_COMMAND_MESSAGE_KEY;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Slf4j
@Component
@Scope(SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class StartCommand implements Command {

   private final Message message;

   @Autowired
   private SubscriberScheduler subscriberScheduler;

   @Autowired
   private SubscriberService subscriberService;

   @Autowired
   private TextMessageProvider textMessageProvider;

   @Override
   public SendMessage execute() {
      SubscriberDto subscriber = addNewSubscriber();
      subscriberScheduler.scheduleRatesJob(subscriber);
      return getResponse(subscriber);
   }

   private SubscriberDto addNewSubscriber() {
      SubscriberDto subscriber = SubscriberConverter.convert(message);
      subscriberService.add(subscriber);
      return subscriber;
   }

   private SendMessage getResponse(SubscriberDto subscriber) {
      return SendMessage.builder()
                        .chatId(subscriber.getChatId().toString())
                        .text(textMessageProvider.getTextMessage(START_COMMAND_MESSAGE_KEY, subscriber))
                        .parseMode(ParseMode.HTML)
                        .replyMarkup(getKeyboard())
                        .build();
   }

   private ReplyKeyboard getKeyboard() {
      KeyboardRow row = new KeyboardRow();
      Currency.getCurrenciesForRate().forEach(currency -> row.add(KeyboardButton.builder().text(currency.getCode()).build()));
      return ReplyKeyboardMarkup.builder().keyboardRow(row).resizeKeyboard(true).build();
   }
}
