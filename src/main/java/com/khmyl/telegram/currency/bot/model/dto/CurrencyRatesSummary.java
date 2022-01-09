package com.khmyl.telegram.currency.bot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyRatesSummary {

   private List<ExchangeRate> rates;
   private String currency;
   private BigDecimal max;
   private BigDecimal min;
   private BigDecimal avg;
   private LocalDateSpan dateSpan;

   public JRBeanCollectionDataSource getDataSource() {
      return new JRBeanCollectionDataSource(rates);
   }

}
