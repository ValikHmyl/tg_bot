package com.khmyl.telegram.currency.bot.deprecated;

import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import com.khmyl.telegram.currency.bot.service.subs.SubscriberService;
import com.khmyl.telegram.currency.bot.text.message.TextMessageProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Deprecated
//@Component
public class StartCommandOld implements IBotCommand {

   @Value("${command.start.name}")
   private String name;
   @Value("${command.start.description}")
   private String description;

   @Autowired
   private SubscriberService subscriberService;

   @Autowired
   private TextMessageProvider<SubscriberDto> textMessageProvider;

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
      SendMessage sendMessage = new SendMessage();
      SubscriberDto subscriber = SubscriberDto.builder()
                                              .id(message.getFrom().getId())
                                              .chatId(message.getChatId())
                                              .name(message.getFrom().getFirstName())
                                              .build();
      subscriberService.add(subscriber);

      sendMessage.setChatId(subscriber.getChatId().toString());
      sendMessage.setText(textMessageProvider.getTextMessage(subscriber));
      try {
         absSender.execute(sendMessage);
      } catch (TelegramApiException e) {
         log.error("Unexpected telegram error", e);
      }

   }
}
