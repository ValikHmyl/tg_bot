package com.khmyl.telegram.currency.bot.service.currency.imp;

import com.khmyl.telegram.currency.bot.model.dto.CurrencyRatesSummary;
import com.khmyl.telegram.currency.bot.model.dto.ExchangeRate;
import com.khmyl.telegram.currency.bot.model.dto.LocalDateSpan;
import com.khmyl.telegram.currency.bot.service.currency.CurrencySummaryCalculator;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;
import org.eclipse.collections.impl.collector.Collectors2;
import org.springframework.stereotype.Component;

import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CurrencySummaryCalculatorImpl implements CurrencySummaryCalculator {

   @Override
   public CurrencyRatesSummary calculate(List<ExchangeRate> rates) {
      BigDecimalSummaryStatistics summaryStatistics = rates.stream()
                                                           .map(ExchangeRate::getRate)
                                                           .collect(Collectors2.summarizingBigDecimal(each -> each));
      List<LocalDate> dates = rates.stream().map(ExchangeRate::getDate).collect(Collectors.toList());
      LocalDateSpan dateSpan = LocalDateSpan.of(dates.stream().min(LocalDate::compareTo).orElse(LocalDate.MIN),
              dates.stream().max(LocalDate::compareTo).orElse(LocalDate.MAX));
      return CurrencyRatesSummary.builder()
                                 .rates(rates)
                                 .currency(rates.iterator().next().getCode())
                                 .max(summaryStatistics.getMax())
                                 .min(summaryStatistics.getMin())
                                 .avg(summaryStatistics.getAverage(new MathContext(5, RoundingMode.HALF_EVEN)))
                                 .dateSpan(dateSpan)
                                 .build();

   }
}
