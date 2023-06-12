package com.khmyl.telegram.currency.bot.model.dto.nbrb;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khmyl.telegram.currency.bot.model.dto.ExchangeRate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NbrbExchangeRate implements ExchangeRate {
   
   @JsonProperty("Cur_ID")
   private Long id;
   @JsonProperty("Date")
   private LocalDate date;
   @JsonProperty("Cur_Abbreviation")
   private String code;
   @JsonProperty("Cur_Scale")
   private Integer amount;
   @JsonProperty("Cur_OfficialRate")
   private BigDecimal rate;

}
