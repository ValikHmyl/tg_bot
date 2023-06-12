package com.khmyl.telegram.currency.bot.service.currency.imp;

import com.khmyl.telegram.currency.bot.model.dto.Currency;
import com.khmyl.telegram.currency.bot.model.dto.ExchangeRate;
import com.khmyl.telegram.currency.bot.model.dto.exchangerateapi.ExchangeRateImpl;
import com.khmyl.telegram.currency.bot.model.dto.nbrb.NbrbExchangeRate;
import com.khmyl.telegram.currency.bot.service.currency.CurrencyRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@Service
@Primary
public class ExchangeRateAPICurrencyRateService implements CurrencyRateService {

   @Autowired
   private WebClient webClient;

   @Value("${exchangerate_api.key}")
   private String apiKey;

   @Override
   public ExchangeRate getRate(String code, LocalDate onDate) {
      return webClient.get()
              .uri(uriBuilder -> uriBuilder.path("v6.exchangerate-api.com/v6/{apiKey}/pair/{from}/{to}")
                      .build(apiKey, code, Currency.BYN.getCode()))
              .retrieve()
              .bodyToMono(ExchangeRateImpl.class)
              .block();
   }

   @Override
   public List<ExchangeRate> getRates(Currency code, LocalDate startDate, LocalDate endDate) {
      throw new UnsupportedOperationException("exchangerate-api does not support for free historical data");
   }
}
