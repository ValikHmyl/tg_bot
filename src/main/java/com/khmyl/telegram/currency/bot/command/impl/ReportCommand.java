package com.khmyl.telegram.currency.bot.command.impl;

import com.khmyl.telegram.currency.bot.command.Command;
import com.khmyl.telegram.currency.bot.command.Response;
import com.khmyl.telegram.currency.bot.command.impl.response.DocumentResponse;
import com.khmyl.telegram.currency.bot.model.dto.ExchangeRate;
import com.khmyl.telegram.currency.bot.service.currency.CurrencyRateService;
import com.khmyl.telegram.currency.bot.service.report.ReportContext;
import com.khmyl.telegram.currency.bot.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.khmyl.telegram.currency.bot.model.dto.Currency.EUR;
import static com.khmyl.telegram.currency.bot.model.dto.Currency.RUB;
import static com.khmyl.telegram.currency.bot.model.dto.Currency.USD;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
@RequiredArgsConstructor
//change to just report command with period param to support diff ranges
public class ReportCommand implements Command {

   private final Message message;
   private final long periodLength;

   @Autowired
   private CurrencyRateService currencyRateService;

   @Autowired
   private ReportService reportService;

   @Override
   public Response execute() {

      Collection<ExchangeRate> rates = currencyRateService.getRates(USD, LocalDate.now().minusDays(periodLength), LocalDate.now());
      rates.addAll(currencyRateService.getRates(EUR, LocalDate.now().minusDays(periodLength), LocalDate.now()));
//      rates.addAll(currencyRateService.getRates(RUB, LocalDate.now().minusDays(10), LocalDate.now()));

      Map<String, Object> parameters = new HashMap<>();
      BigDecimal var = new BigDecimal("0.01");
      //get max/min from stats
      parameters.put("min", rates.stream().map(ExchangeRate::getRate).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO).subtract(var).toString());
      parameters.put("max", rates.stream().map(ExchangeRate::getRate).max(BigDecimal::compareTo).orElse(BigDecimal.ONE).add(var).toString());

      ReportContext<ExchangeRate> reportContext = ReportContext.<ExchangeRate>builder()
                                                               .datasource(rates)
                                                               .parameters(parameters)
                                                               .templateName("/report/currency_report.jrxml")
                                                               .reportName("test")
                                                               .build();

      Path reportPath = reportService.generateReport(reportContext);

      return DocumentResponse.of(SendDocument.builder()
                                             .chatId(message.getChatId().toString())
                                             .document(new InputFile(reportPath.toFile()))
                                             .caption("Weekly report of currencies")
                                             .build());
   }
}
