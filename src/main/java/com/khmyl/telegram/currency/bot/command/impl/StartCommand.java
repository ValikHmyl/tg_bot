package com.khmyl.telegram.currency.bot.command.impl;

import com.khmyl.telegram.currency.bot.command.Command;
import com.khmyl.telegram.currency.bot.command.Response;
import com.khmyl.telegram.currency.bot.command.impl.response.decorator.MessageWithDefaultKeyboard;
import com.khmyl.telegram.currency.bot.command.impl.response.MessageResponse;
import com.khmyl.telegram.currency.bot.converter.SubscriberConverter;
import com.khmyl.telegram.currency.bot.kafka.KafkaSender;
import com.khmyl.telegram.currency.bot.message.text.TextMessageProvider;
import com.khmyl.telegram.currency.bot.message.text.impl.template.StartMessageTemplate;
import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import com.khmyl.telegram.currency.bot.service.subs.SubscriberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Slf4j
@Component
@Scope(SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class StartCommand implements Command {

   private final Message message;

   @Autowired
   private SubscriberService subscriberService;

   @Autowired
   private TextMessageProvider textMessageProvider;

   @Autowired
   private KafkaSender kafkaSender;

   @Override
   public Response execute() {
      SubscriberDto subscriber = addNewSubscriber();
      kafkaSender.sendOnSubscribeMessage(subscriber);
      return getResponse(subscriber);
   }

   private SubscriberDto addNewSubscriber() {
      SubscriberDto subscriber = SubscriberConverter.convert(message);
      subscriberService.add(subscriber);
      return subscriber;
   }

   private Response getResponse(SubscriberDto subscriber) {
      SendMessage responseMessage = MessageWithDefaultKeyboard.toBuilder()
                                                      .chatId(subscriber.getChatId().toString())
                                                      .text(textMessageProvider.getTextMessage(new StartMessageTemplate(subscriber)))
                                                      .build();
      return MessageResponse.of(responseMessage);
   }

}
