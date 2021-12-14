package com.khmyl.telegram.currency.bot.command.impl;

import com.khmyl.telegram.currency.bot.command.Command;
import com.khmyl.telegram.currency.bot.command.Response;
import com.khmyl.telegram.currency.bot.command.impl.response.DocumentResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class WeeklyReportCommand implements Command {

   @SneakyThrows
   @Override
   public Response execute() {
      Path tempFile = Files.createTempFile(null, null);

      // Writes a string to the above temporary file
      Files.write(tempFile, "test".getBytes(StandardCharsets.UTF_8));

      return DocumentResponse.of(SendDocument.builder().document(new InputFile(tempFile.toFile())).caption("ss").build());
   }
}
