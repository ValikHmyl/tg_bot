package com.khmyl.telegram.currency.bot.service.currency;

import com.khmyl.telegram.currency.bot.model.dto.Currency;
import com.khmyl.telegram.currency.bot.model.dto.ExchangeRate;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyRateService {

   ExchangeRate getRate(String code, LocalDate onDate);

   List<ExchangeRate> getRates(Currency code, LocalDate startDate, LocalDate endDate);
}
