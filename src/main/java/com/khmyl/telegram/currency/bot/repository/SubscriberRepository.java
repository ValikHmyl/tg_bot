package com.khmyl.telegram.currency.bot.repository;

import com.khmyl.telegram.currency.bot.model.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {


}
