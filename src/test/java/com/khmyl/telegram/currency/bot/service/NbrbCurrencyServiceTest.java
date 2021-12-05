package com.khmyl.telegram.currency.bot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.khmyl.telegram.currency.bot.TestWithWebServer;
import com.khmyl.telegram.currency.bot.model.dto.ExchangeRate;
import com.khmyl.telegram.currency.bot.model.dto.NbrbExchangeRate;
import com.khmyl.telegram.currency.bot.service.currency.CurrencyRateService;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
@ActiveProfiles("test")
public class NbrbCurrencyServiceTest extends TestWithWebServer {

   @Autowired
   private WebClient webClient;

   @Autowired
   private CurrencyRateService currencyRateService;

   @Test
   public void testGetRate_subsequentCallsReturnedFromCache() throws Exception {
      String code = "USD";
      LocalDate date = LocalDate.now();

      NbrbExchangeRate exchangeRate = NbrbExchangeRate.builder().id(1L).code(code).date(date).rate(new BigDecimal("2.4")).amount(1).build();

      mockWebServer.enqueue(exchangeResponse(exchangeRate));

      ExchangeRate actual = currencyRateService.getRate(code, date);
      Mockito.verify(webClient).get();
      Assertions.assertEquals(exchangeRate, actual);
      currencyRateService.getRate(code, date);
      Mockito.verifyNoMoreInteractions(webClient);

   }

   private MockResponse exchangeResponse(NbrbExchangeRate exchangeRate) throws JsonProcessingException {
      return new MockResponse().setBody(objectMapper.writeValueAsString(exchangeRate))
                               .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
   }
}
