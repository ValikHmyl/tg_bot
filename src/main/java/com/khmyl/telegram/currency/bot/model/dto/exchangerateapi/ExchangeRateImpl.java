package com.khmyl.telegram.currency.bot.model.dto.exchangerateapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khmyl.telegram.currency.bot.model.dto.ExchangeRate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

//exchangerate-api.com

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExchangeRateImpl implements ExchangeRate {

   @JsonProperty("time_last_update_unix")
   @JsonDeserialize(using = UnixDateDeserializer.class)
   private LocalDate date;
   @JsonProperty("base_code")
   private String code;
   private Integer amount;
   @JsonProperty("conversion_rate")
   private BigDecimal rate;


   public Integer getAmount() {
      return 1;
   }
}
