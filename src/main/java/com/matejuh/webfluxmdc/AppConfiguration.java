package com.matejuh.webfluxmdc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class AppConfiguration {

    private static final Logger webClientLogger = LoggerFactory.getLogger("webClient");

    @Bean
    public FilterRegistrationBean<RequestContextFilter> requestContextSettingFilter() {
        FilterRegistrationBean<RequestContextFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RequestContextFilter());
        registration.setName("REQUEST_CONTEXT");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilter() {
        FilterRegistrationBean<LoggingFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new LoggingFilter());
        registration.setName("LOGGING");
        registration.setOrder(2);
        return registration;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().filter((request, next) ->
                next.exchange(request)
                    .doOnSuccess(response ->
                            webClientLogger.info(
                                    "method={} uri={} status={}",
                                    request.method(),
                                    request.url(),
                                    response.statusCode()
                            )
                    )
        ).build();
    }
}
