package com.khmyl.telegram.currency.bot.service.currency.imp;

import com.khmyl.telegram.currency.bot.model.dto.ExchangeRate;
import com.khmyl.telegram.currency.bot.model.dto.NbrbExchangeRate;
import com.khmyl.telegram.currency.bot.service.currency.CurrencyRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@Service
public class NbrbCurrencyRateService implements CurrencyRateService {

   @Autowired
   private WebClient webClient;

   @Override
   @Cacheable("rates")
   public ExchangeRate getRate(String code, LocalDate onDate) {
      return webClient.get()
              .uri(uriBuilder -> uriBuilder.path("/rates/{code}")
                      .queryParam("parammode", "2")
                      .queryParam("ondate", onDate)
                      .build(code))
              .retrieve()
              .bodyToMono(NbrbExchangeRate.class)
              .block();
   }

   @Override
   @Cacheable("rates")
   public List<? extends ExchangeRate> getRates(String code, LocalDate startDate, LocalDate endDate) {
      return webClient.get()
              .uri(uriBuilder -> uriBuilder.path("/rates/dynamics/{code}")
                      .queryParam("startdate", startDate)
                      .queryParam("enddate", endDate)
                      .build(code))
              .retrieve()
              .bodyToMono(new ParameterizedTypeReference<List<NbrbExchangeRate>>() {})
              .block();
   }
}
