package com.khmyl.telegram.currency.bot.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NbrbCurrency {
   
   @JsonProperty("Cur_ID")
   private Long id;
   @JsonProperty("Cur_ParentID ")
   private Long parentId;
   @JsonProperty("Cur_Abbreviation")
   private String code;
   @JsonProperty("Cur_DateStart")
   private LocalDate startDate;
   @JsonProperty("Cur_DateEnd")
   private LocalDate endDate;

}
