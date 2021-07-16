package com.khmyl.telegram.currency.bot.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ExchangeRate {

   LocalDate getDate();

   String getCode();

   Integer getAmount();

   BigDecimal getRate();
}
