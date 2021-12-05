package com.khmyl.telegram.currency.bot.message.text.impl;

import com.khmyl.telegram.currency.bot.message.text.MessageTemplate;
import com.khmyl.telegram.currency.bot.message.text.MessageTemplateFactory;
import com.khmyl.telegram.currency.bot.util.BeanUtil;
import org.springframework.stereotype.Component;

@Component
public class MessageTemplateByKeyFactory implements MessageTemplateFactory {

   @Override
   public MessageTemplate getTemplate(String key, Object... args) {
      return BeanUtil.getBean(key, args);
   }

}
