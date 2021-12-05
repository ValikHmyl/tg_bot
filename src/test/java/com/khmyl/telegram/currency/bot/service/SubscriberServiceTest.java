package com.khmyl.telegram.currency.bot.service;

import com.khmyl.telegram.currency.bot.model.dto.SubscriberDto;
import com.khmyl.telegram.currency.bot.model.entity.Subscriber;
import com.khmyl.telegram.currency.bot.repository.SubscriberRepository;
import com.khmyl.telegram.currency.bot.service.subs.SubscriberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class SubscriberServiceTest {

   @Autowired
   private SubscriberService underTest;

   @MockBean
   private SubscriberRepository subscriberRepository;

   @Test
   public void testGetById_firstFromRepo_secondFromCache() {
      when(subscriberRepository.findById(anyLong())).thenReturn(Optional.of(new Subscriber()));
      Optional<SubscriberDto> actual = underTest.getById(1L);
      verify(subscriberRepository).findById(anyLong());
      Assertions.assertTrue(actual.isPresent());
      underTest.getById(1L);
      verifyNoMoreInteractions(subscriberRepository);
   }

   @Test
   public void testAdd_thenGetFromCache() {
      when(subscriberRepository.findById(anyLong())).thenReturn(Optional.of(new Subscriber()));
      when(subscriberRepository.existsById(anyLong())).thenReturn(false);

      SubscriberDto actual = underTest.add(SubscriberDto.builder().id(12L).build());

      Assertions.assertEquals(12L, actual.getId());
      verify(subscriberRepository).save(any());
      verify(subscriberRepository).existsById(anyLong());

      underTest.getById(12L);
      verifyNoMoreInteractions(subscriberRepository);

   }

}
