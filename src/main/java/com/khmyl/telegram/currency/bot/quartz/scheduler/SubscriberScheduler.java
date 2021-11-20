package com.khmyl.telegram.currency.bot.quartz.scheduler;

import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;

public interface SubscriberScheduler {

   void scheduleRatesJob(SubscriberDto subscriber);

   void deleteSubJobs(SubscriberDto dto);
}
