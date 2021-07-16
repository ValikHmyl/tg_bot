package com.khmyl.telegram.currency.bot.service.currency;

import com.khmyl.telegram.currency.bot.model.dto.ExchangeRate;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyRateService {

   ExchangeRate getRate(String code, LocalDate onDate);

   List<? extends ExchangeRate> getRates(String code, LocalDate startDate, LocalDate endDate);
}
