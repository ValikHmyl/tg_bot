package com.khmyl.telegram.currency.bot.quartz.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Slf4j
@Configuration
public class QuartzSchedulerConfig {

   @Autowired
   private DataSource dataSource;

   @Bean
   public Scheduler scheduler(SchedulerFactoryBean factory) throws SchedulerException {
      Scheduler scheduler = factory.getScheduler();
      scheduler.start();
      return scheduler;
   }

   @Bean
   public SchedulerFactoryBean schedulerFactoryBean(SpringBeanJobFactory springBeanJobFactory) throws IOException {
      SchedulerFactoryBean factory = new SchedulerFactoryBean();
      factory.setJobFactory(springBeanJobFactory);
      factory.setQuartzProperties(quartzProperties());
      factory.setDataSource(dataSource);
      return factory;
   }

   public Properties quartzProperties() throws IOException {
      PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
      propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
      propertiesFactoryBean.afterPropertiesSet();
      return propertiesFactoryBean.getObject();
   }

}
