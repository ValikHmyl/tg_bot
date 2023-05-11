package com.khmyl.telegram.currency.bot.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

import javax.net.ssl.SSLException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

   public static final int DEFAULT_TIMEOUT = 5000;

   @Value(value = "${proxy.rb.host}")
   private String proxyHost;

   @Value(value = "${proxy.rb.port}")
   private int proxyPort;

   @Bean
   public WebClient httpsNbrbWebClient() throws SSLException {
      SslContext context = SslContextBuilder.forClient()
              .trustManager(InsecureTrustManagerFactory.INSTANCE)
              .build();

      HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(context))
              .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, DEFAULT_TIMEOUT)
              .responseTimeout(Duration.ofMillis(DEFAULT_TIMEOUT))
              .proxy(proxy -> proxy.type(ProxyProvider.Proxy.HTTP)
                      .host(proxyHost)
                      .port(proxyPort))
              .doOnConnected(conn ->
                      conn.addHandlerLast(new ReadTimeoutHandler(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS))
                              .addHandlerLast(new WriteTimeoutHandler(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)));

      return WebClient.builder()
              .clientConnector(new ReactorClientHttpConnector(httpClient))
              .baseUrl("www.nbrb.by/api/exrates")
              .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
              .build();
   }

}
