package com.khmyl.telegram.currency.bot.service.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportContext<T> {

   private Collection<T> datasource;
   private Map<String, Object> parameters;
   private String templateName;
   private String reportName;
}
