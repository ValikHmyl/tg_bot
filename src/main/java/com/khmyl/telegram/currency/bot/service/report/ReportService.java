package com.khmyl.telegram.currency.bot.service.report;

import java.nio.file.Path;

public interface ReportService {

   <T> Path generateReport(ReportContext<T> reportContext);

}
