package com.khmyl.telegram.currency.bot.service.currency;

import com.khmyl.telegram.currency.bot.model.dto.CurrencyRatesSummary;
import com.khmyl.telegram.currency.bot.model.dto.ExchangeRate;

import java.util.List;

public interface CurrencySummaryCalculator {

   CurrencyRatesSummary calculate(List<ExchangeRate> rates);
}
