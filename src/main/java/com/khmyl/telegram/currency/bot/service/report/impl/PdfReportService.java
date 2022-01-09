package com.khmyl.telegram.currency.bot.service.report.impl;

import com.khmyl.telegram.currency.bot.service.report.ReportContext;
import com.khmyl.telegram.currency.bot.service.report.ReportException;
import com.khmyl.telegram.currency.bot.service.report.ReportService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component("PdfReportService")
public class PdfReportService implements ReportService {

   @Override
   public <T> Path generateReport(ReportContext<T> reportContext) {
      try {
         InputStream stream = this.getClass().getResourceAsStream(reportContext.getTemplateName());
         JasperReport report = JasperCompileManager.compileReport(stream);
         JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(reportContext.getDatasource());
         JasperPrint print = JasperFillManager.fillReport(report, reportContext.getParameters(), source);

         String destFileName = "." + FileSystems.getDefault().getSeparator() + reportContext.getReportName() + ".pdf";
         JasperExportManager.exportReportToPdfFile(print, destFileName);
         return Paths.get(destFileName);
      } catch (JRException ex) {
         log.error("Error occurred while generating report", ex);
         throw new ReportException(ex);
      }
   }

   @Override
   public JasperReport compileSubReport(String subReportName) {
      try {
         InputStream stream = this.getClass().getResourceAsStream(subReportName);
         return JasperCompileManager.compileReport(stream);
      } catch (JRException ex) {
         log.error("Error occurred while compiling subreport", ex);
         throw new ReportException(ex);
      }
   }
}
