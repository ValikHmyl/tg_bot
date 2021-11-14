package com.khmyl.telegram.currency.bot.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanUtil implements ApplicationContextAware {

   private static ApplicationContext context;

   @Override
   public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
      context = applicationContext;
   }

   public static <T> T getBean(Class<T> requiredType, Object... args) {
      return context.getBean(requiredType, args);
   }

   public static <T> T getBean(String name, Object... args) {
      return (T) context.getBean(name, args);
   }

}
