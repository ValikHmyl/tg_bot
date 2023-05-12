package com.khmyl.telegram.currency.bot.command.impl;

import com.khmyl.telegram.currency.bot.command.Command;
import com.khmyl.telegram.currency.bot.command.Response;
import com.khmyl.telegram.currency.bot.command.impl.response.DocumentResponse;
import com.khmyl.telegram.currency.bot.message.text.TextMessageConstants;
import com.khmyl.telegram.currency.bot.message.text.TextMessageProvider;
import com.khmyl.telegram.currency.bot.message.text.impl.template.ReportCaptionTemplate;
import com.khmyl.telegram.currency.bot.model.dto.Currency;
import com.khmyl.telegram.currency.bot.model.dto.CurrencyRatesSummary;
import com.khmyl.telegram.currency.bot.model.dto.LocalDateSpan;
import com.khmyl.telegram.currency.bot.service.currency.CurrencyRateService;
import com.khmyl.telegram.currency.bot.service.currency.CurrencySummaryCalculator;
import com.khmyl.telegram.currency.bot.service.report.ReportContext;
import com.khmyl.telegram.currency.bot.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
@RequiredArgsConstructor
//change to just report command with period param to support diff ranges
public class ReportCommand implements Command {

   private static final String CURRENCY_REPORT_PATH = "/report/currency_report.jrxml";
   private static final String CHART_SUBREPORT_PATH = "/report/chart_subreport.jrxml";
   private static final String REPORT_NAME = "currency_report_%s_%s";

   private final Message message;
   private final long periodLength;

   @Autowired
   private CurrencyRateService currencyRateService;

   @Autowired
   private ReportService reportService;

   @Autowired
   private CurrencySummaryCalculator summaryCalculator;

   @Autowired
   private TextMessageProvider messageProvider;

   @Override
   public Response execute() {

      LocalDateSpan dateSpan = LocalDateSpan.of(LocalDate.now().minusDays(periodLength), LocalDate.now());
      Collection<CurrencyRatesSummary> currencyRatesSummaries = Currency.getCurrenciesForRate()
                                                                  .stream()
                                                                  .map(currency -> currencyRateService.getRates(currency,
                                                                          dateSpan.getStartDate(),
                                                                          dateSpan.getEndDate()))
                                                                  .map(summaryCalculator::calculate)
                                                                  .collect(Collectors.toList());

      Map<String, Object> parameters = new HashMap<>();
      parameters.put("SUB_REPORT", reportService.compileSubReport(CHART_SUBREPORT_PATH));
      ReportContext<CurrencyRatesSummary> reportContext = ReportContext.<CurrencyRatesSummary>builder()
                                                               .datasource(currencyRatesSummaries)
                                                               .parameters(parameters)
                                                               .templateName(CURRENCY_REPORT_PATH)
                                                               .reportName(String.format(REPORT_NAME, dateSpan.getStartDate(), dateSpan.getEndDate()))
                                                               .build();

      Path reportPath = reportService.generateReport(reportContext);

      return DocumentResponse.of(SendDocument.builder()
                                             .chatId(message.getChatId().toString())
                                             .document(new InputFile(reportPath.toFile()))
                                             .caption(messageProvider.getTextMessage(new ReportCaptionTemplate()))
                                             .build());
   }
}
