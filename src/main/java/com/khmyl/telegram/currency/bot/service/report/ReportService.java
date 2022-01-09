package com.khmyl.telegram.currency.bot.service.report;

import net.sf.jasperreports.engine.JasperReport;

import java.nio.file.Path;

public interface ReportService {

   <T> Path generateReport(ReportContext<T> reportContext);

   JasperReport compileSubReport(String subReportName);
}
