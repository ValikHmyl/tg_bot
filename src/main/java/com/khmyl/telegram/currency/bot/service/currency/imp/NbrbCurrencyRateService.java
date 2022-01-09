package com.khmyl.telegram.currency.bot.service.currency.imp;

import com.khmyl.telegram.currency.bot.model.dto.Currency;
import com.khmyl.telegram.currency.bot.model.dto.ExchangeRate;
import com.khmyl.telegram.currency.bot.model.dto.NbrbCurrency;
import com.khmyl.telegram.currency.bot.model.dto.NbrbExchangeRate;
import com.khmyl.telegram.currency.bot.service.currency.CurrencyRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
   public List<ExchangeRate> getRates(Currency currency, LocalDate startDate, LocalDate endDate) {
      List<Long> effectiveCurrencyIds = getEffectiveCurrencyIds(currency, startDate, endDate);
      return effectiveCurrencyIds.stream()
                                 .map(id -> getRates(id, startDate, endDate))
                                 .filter(Objects::nonNull)
                                 .flatMap(Collection::stream)
                                 .peek(rate -> rate.setCode(currency.getCode()))
                                 .collect(Collectors.toList());
   }

   private List<NbrbExchangeRate> getRates(Long currencyId, LocalDate startDate, LocalDate endDate) {
      return webClient.get()
                      .uri(uriBuilder -> uriBuilder.path("/rates/dynamics/{cur_id}")
                                                   .queryParam("startdate", startDate)
                                                   .queryParam("enddate", endDate)
                                                   .build(currencyId))
                      .retrieve()
                      .bodyToMono(new ParameterizedTypeReference<List<NbrbExchangeRate>>() {
                      })
                      .block();
   }

   private List<Long> getEffectiveCurrencyIds(Currency currency, LocalDate startDate, LocalDate endDate) {
      List<NbrbCurrency> allCurrencies = webClient.get()
                                                  .uri(uriBuilder -> uriBuilder.path("/currencies").build())
                                                  .retrieve()
                                                  .bodyToMono(new ParameterizedTypeReference<List<NbrbCurrency>>() {
                                                  })
                                                  .block();
      if (allCurrencies == null) {
         return Collections.emptyList();
      }
      List<NbrbCurrency> requested = allCurrencies.stream()
                                                  .filter(cur -> cur.getCode().equals(currency.getCode()))
                                                  .collect(Collectors.toList());
      if (requested.size() == 1) {
         return requested.stream().map(NbrbCurrency::getId).collect(Collectors.toList());
      }
      return requested.stream()
                      .filter(cur -> !cur.getStartDate().isAfter(startDate) || !cur.getEndDate().isBefore(endDate))
                      .map(NbrbCurrency::getId)
                      .collect(Collectors.toList());
   }
}
